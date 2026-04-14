/**
    JoinGameCommand allows a user to join an existing game session.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class JoinGameCommand {

    private final GameSessionService sessionService;

    public JoinGameCommand(GameSessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
        Executes the join game command.
    */

    public void execute(SlashCommandInteractionEvent event) {

        long guildId = event.getGuild().getIdLong();

        GameSession session = sessionService.getSession(guildId);

        if (session == null) {
            event.reply("No active game. Use /startgame first.").setEphemeral(true).queue();
            return;
        }

        Player player = new Player(event.getUser().getId(), event.getUser().getName());
        session.addPlayer(player);

        event.reply(player.getUsername() + " joined the game.").queue();
    }
}