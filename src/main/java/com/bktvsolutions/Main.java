/**
    Main file for DND game in Discord. Main application entry point is responsible for loading configuration,
    creating the bot components, and starting the Discord bot. Current file responsibilities:
        1. Ping Command & Roll -> Cmd logic for action /ping & /roll
        2. Command Manager -> Stores cmds
        3. Command Service -> Finds & runs cmds
        4. Bot Launcher -> Controlling JDA bot instance
        5. Bot Listener -> Handling discord events
        6. Dice -> Handling dice rolls and definition of dice


    Aiming to create enterprise-grade distributed game server that includes:
        - Multi-player session management
        - Turn-based engine (state machine)
        - Persistent storage (SQLite/PostgreSQL)
        - Command handling system (modular)
        - Game state recovery after restart
        - Role-based permissions (DM vs players)
    Bonus:
        - Add Web dashboard which is where the DM or Admin can:
            * create campaigns
            * edit characters
            * assign stats/items
            * view combat order
            * check logs/history
            * load/save sessions
        - RESTAPI alongside JDA
        - Logging + error handling system

*/

package com.bktvsolutions;

import com.bktvsolutions.bot.BotLauncher;
import com.bktvsolutions.bot.BotListener;
import com.bktvsolutions.config.Config;
import com.bktvsolutions.command.CommandManager;

/**
    Main entry point for the JDA DND bot application.
*/

public final class Main {
    private Main() {
        throw new UnsupportedOperationException("Main class cannot be instantiated");
    }

    /**
        Starts the bot application.
    */

    public static void main(String[] args) {
        try {
            String token = Config.getDiscordToken();

            CommandManager commandManager = new CommandManager();
            BotListener botListener = new BotListener(commandManager);
            BotLauncher botLauncher = new BotLauncher(token, botListener);

            botLauncher.start();
        } catch (Exception exception) {
            System.err.println("Failed to start bot");
            exception.printStackTrace();
        }
    }
}