/**
    Main file for DND game in Discord. Main application entry point is responsible for loading configuration,
    creating the bot components, and starting the Discord bot. Current file responsibilities:
        1. Ping Command -> Cmd logic
        2. Command Manager -> Stores cmds
        3. Command Service -> Finds & runs cmds
        4. Bot Launcher -> Controlling JDA bot instance
        5. Bot Listener -> Handling discord events
        6.
*/

package com.bktvsolutions;

import com.bktvsolutions.bot.BotLauncher;
import com.bktvsolutions.bot.BotListener;
import com.bktvsolutions.config.Config;

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

            BotListener botListener = new BotListener();
            BotLauncher botLauncher = new BotLauncher(token, botListener);

            botLauncher.start();
        } catch (Exception exception) {
            System.err.println("Failed to start bot");
            exception.printStackTrace();
        }
    }
}