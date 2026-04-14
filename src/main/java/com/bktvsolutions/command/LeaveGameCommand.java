/**
    LeaveGameCommand allows a player to leave the current game session.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

/**
    Removes the current player from the active game session.
*/

public class LeaveGameCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates a leave game command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public LeaveGameCommand(GameSessionService gameSessionService) {
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
        return "leavegame";
    }

    /**
        Executes the leave game command.

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

        String userId = event.getUser().getId();
        List<Player> players = session.getPlayers();

        for (int index = 0; index < players.size(); index++) {
            Player player = players.get(index);

            if (player.getId().equals(userId)) {
                player.setInGame(false);
                players.remove(index);

                if (players.isEmpty()) {
                    session.end();
                    gameSessionService.removeSession(guildId);

                    event.reply("You left the game. No players remain, so the session was ended.")
                            .queue();
                    return;
                }

                event.reply(player.getUsername() + " left the game.")
                        .queue();
                return;
            }
        }

        event.reply("You are not currently in the game.")
                .setEphemeral(true)
                .queue();
    }
}