/**
    JoinGameCommand allows a Discord user to join an existing game session.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class JoinGameCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates a join game command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public JoinGameCommand(GameSessionService gameSessionService) {
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
        return "join";
    }

    /**
        Executes the join game command.

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

        String userId = event.getUser().getId();
        String username = event.getUser().getName();

        for (Player existingPlayer : session.getPlayers()) {
            if (existingPlayer.getId().equals(userId)) {
                event.reply("You have already joined this game.")
                        .setEphemeral(true)
                        .queue();
                return;
            }
        }

        Player player = new Player(userId, username);
        session.addPlayer(player);

        event.reply(username + " joined the game.")
                .queue();
    }
}