/**
    StartGameCommand creates a new game session for the current guild.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StartGameCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates a start game command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public StartGameCommand(GameSessionService gameSessionService) {
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
        return "startgame";
    }

    /**
        Executes the start game command.

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

        if (gameSessionService.hasSession(guildId)) {
            event.reply("A game session already exists in this server.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        gameSessionService.createSession(guildId);

        event.reply("Game session created. Players can now join with /join.")
                .queue();
    }
}