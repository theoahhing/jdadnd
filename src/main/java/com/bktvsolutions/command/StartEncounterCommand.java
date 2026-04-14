/**
    StartEncounterCommand starts the active game session and begins turn order.

    Initiative is rolled for each player and used to determine encounter order.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import com.bktvsolutions.service.InitiativeService;
import com.bktvsolutions.service.TurnService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

public class StartEncounterCommand implements Command {
    private final GameSessionService gameSessionService;
    private final InitiativeService initiativeService;

    /**
        Creates a start encounter command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public StartEncounterCommand(GameSessionService gameSessionService) {
        if (gameSessionService == null) {
            throw new IllegalArgumentException("gameSessionService cannot be null");
        }

        this.gameSessionService = gameSessionService;
        this.initiativeService = new InitiativeService();
    }

    /**
        Creates a start encounter command with the provided services.

        @param gameSessionService session service to use
        @param initiativeService initiative service to use
        @throws IllegalArgumentException if either dependency is null
    */

    public StartEncounterCommand(GameSessionService gameSessionService, InitiativeService initiativeService) {
        if (gameSessionService == null) {
            throw new IllegalArgumentException("gameSessionService cannot be null");
        }

        if (initiativeService == null) {
            throw new IllegalArgumentException("initiativeService cannot be null");
        }

        this.gameSessionService = gameSessionService;
        this.initiativeService = initiativeService;
    }

    /**
        Gets the name of the command as used in Discord.

        @return command name
    */

    @Override
    public String getName() {
        return "startencounter";
    }

    /**
        Executes the start encounter command.

        @param event slash command interaction event
    */

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        if (event.getGuild() == null) {
            event.reply("This command can only be used in a server.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        long guildId = event.getGuild().getIdLong();
        GameSession session = gameSessionService.getSession(guildId);

        if (session == null) {
            event.reply("No game session exists yet. Use /startgame first.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (session.isActive()) {
            event.reply("The encounter has already started.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (session.getPlayers().isEmpty()) {
            event.reply("Cannot start encounter with no players. Use /join first.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        for (Player player : session.getPlayers()) {
            if (!player.hasCharacter()) {
                event.reply("All players must have an assigned character before starting the encounter.")
                        .setEphemeral(true)
                        .queue();
                return;
            }
        }

        List<InitiativeService.InitiativeEntry> initiativeEntries =
                initiativeService.rollInitiativeWithDetails(session.getPlayers());

        List<Player> sortedPlayers = initiativeEntries.stream()
                .map(InitiativeService.InitiativeEntry::getPlayer)
                .toList();

        session.start();

        TurnService turnService = session.getTurnService();
        Player currentPlayer = turnService.getCurrentPlayer();

        StringBuilder response = new StringBuilder();
        response.append("Encounter started.\n");
        response.append("Initiative order:\n");

        for (int index = 0; index < initiativeEntries.size(); index++) {
            InitiativeService.InitiativeEntry entry = initiativeEntries.get(index);

            response.append(index + 1)
                    .append(". ")
                    .append(entry.getPlayer().getUsername())
                    .append(" (")
                    .append(entry.getRoll());

            if (entry.getModifier() >= 0) {
                response.append(" + ").append(entry.getModifier());
            } else {
                response.append(" - ").append(Math.abs(entry.getModifier()));
            }

            response.append(" = ")
                    .append(entry.getTotal())
                    .append(")\n");
        }

        response.append("\nIt is now ")
                .append(currentPlayer.getUsername())
                .append("'s turn. Round ")
                .append(turnService.getRoundNumber())
                .append(".");

        event.reply(response.toString()).queue();
    }
}