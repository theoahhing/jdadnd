/**
    StartGameCommand initializes a new game session for the guild.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StartGameCommand {

    private final GameSessionService sessionService;

    public StartGameCommand(GameSessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
        Executes the start game command.
    */

    public void execute(SlashCommandInteractionEvent event) {

        long guildId = event.getGuild().getIdLong();

        if (sessionService.hasSession(guildId)) {
            event.reply("A game is already running.").setEphemeral(true).queue();
            return;
        }

        sessionService.createSession(guildId);

        event.reply("Game session created. Players can now join using /join.").queue();
    }
}