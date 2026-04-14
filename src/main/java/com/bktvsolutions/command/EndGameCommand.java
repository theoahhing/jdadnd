/**
    EndGameCommand ends the current game session and clears all associated state.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class EndGameCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates an end game command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public EndGameCommand(GameSessionService gameSessionService) {
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
        return "endgame";
    }

    /**
        Executes the end game command.

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
            event.reply("No active game session to end.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        session.end();
        gameSessionService.removeSession(guildId);

        event.reply("Game session has been ended and cleared.")
                .queue();
    }
}