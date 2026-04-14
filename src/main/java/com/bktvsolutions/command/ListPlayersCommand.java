/**
    ListPlayersCommand displays all players currently in the game session,
    including their assigned character and class if available.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

/**
    Lists players in the current game session.
*/

public class ListPlayersCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates a list players command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public ListPlayersCommand(GameSessionService gameSessionService) {
        if (gameSessionService == null) {
            throw new IllegalArgumentException("gameSessionService cannot be null");
        }

        this.gameSessionService = gameSessionService;
    }

    /**
        Gets the name of the command as used in Discord.

        @return command name
    */

    @Override
    public String getName() {
        return "listplayers";
    }

    /**
        Executes the list players command.

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
            event.reply("No active game session. Use /startgame first.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        List<Player> players = session.getPlayers();

        if (players.isEmpty()) {
            event.reply("No players have joined the game yet.")
                    .queue();
            return;
        }

        StringBuilder response = new StringBuilder();
        response.append("Players in current game:\n");

        for (int index = 0; index < players.size(); index++) {
            Player player = players.get(index);

            response.append(index + 1)
                    .append(". ")
                    .append(player.getUsername());

            if (player.hasCharacter()) {
                response.append(" - ")
                        .append(player.getCharacter().getName())
                        .append(" (")
                        .append(player.getCharacter().getCharacterClass())
                        .append(")");
            } else {
                response.append(" - No character assigned");
            }

            response.append("\n");
        }

        event.reply(response.toString()).queue();
    }
}