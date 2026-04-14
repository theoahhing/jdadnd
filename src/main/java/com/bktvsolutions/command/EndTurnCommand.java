/**
    EndTurnCommand advances the game to the next player's turn.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import com.bktvsolutions.service.TurnService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class EndTurnCommand {

    private final GameSessionService sessionService;

    public EndTurnCommand(GameSessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
        Executes the end turn command.
    */

    public void execute(SlashCommandInteractionEvent event) {

        long guildId = event.getGuild().getIdLong();

        GameSession session = sessionService.getSession(guildId);

        if (session == null || !session.isActive()) {
            event.reply("No active game.").setEphemeral(true).queue();
            return;
        }

        TurnService turnService = session.getTurnService();

        turnService.nextTurn();
        Player currentPlayer = turnService.getCurrentPlayer();

        event.reply("It is now " + currentPlayer.getUsername()
                + "'s turn. (Round " + turnService.getRoundNumber() + ")").queue();
    }
}