/**
    EndTurnCommand advances the current game session to the next player's turn.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import com.bktvsolutions.service.TurnService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class EndTurnCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates an end turn command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public EndTurnCommand(GameSessionService gameSessionService) {
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
        return "endturn";
    }

    /**
        Executes the end turn command.

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

        if (!session.isActive()) {
            event.reply("The game has not started yet.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        TurnService turnService = session.getTurnService();

        turnService.nextTurn();

        Player currentPlayer = turnService.getCurrentPlayer();

        event.reply("It is now " + currentPlayer.getUsername()
                        + "'s turn. Round " + turnService.getRoundNumber() + ".")
                .queue();
    }
}