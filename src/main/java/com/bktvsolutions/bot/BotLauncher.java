/**
    Bot launcher class is responsible for creating, starting, and shutting down
    the JDA instance used by the Discord bot.
*/

package com.bktvsolutions.bot;

import com.bktvsolutions.config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

/**
    Starts and manages the lifecycle of the Discord bot.
*/

public class BotLauncher {
    private final String token;
    private final BotListener botListener;

    private JDA jda;

    /**
        Creates a bot launcher with the provided token and listener.

        @throws IllegalArgumentException if token is null or blank
        @throws IllegalArgumentException if botListener is null
    */

    public BotLauncher(String token, BotListener botListener) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("token cannot be null or blank");
        }

        if (botListener == null) {
            throw new IllegalArgumentException("botListener cannot be null");
        }

        this.token = token;
        this.botListener = botListener;
    }

    /**
        Starts the Discord bot and waits until it is ready.

        @throws LoginException if the token is invalid
        @throws InterruptedException if startup is interrupted
        @throws IllegalStateException if the bot is already running
    */

    public void start() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(token)
                .addEventListeners(botListener)
                .build();

        jda.awaitReady();

        String guildId = Config.getDiscordGuildId();

        var guild = jda.getGuildById(guildId);

        if (guild != null) {
            guild.updateCommands()
                    .addCommands(
                            Commands.slash("startgame", "Starts a new game session"),
                            Commands.slash("join", "Joins the current game session"),
                            Commands.slash("startencounter", "Starts the encounter and begins turn order"),
                            Commands.slash("endturn", "Ends the current turn"),
                            Commands.slash("ping", "Checks if the bot is responding"),
                                                        Commands.slash("roll", "Rolls a die")
                                    .addOption(OptionType.INTEGER, "sides", "Number of sides on the die", false)
                    )
                    .queue();
        }
    }

    /**
        Stops the bot if it is running.
    */

    public void stop() {
        if (jda == null) {
            return;
        }

        try {
            jda.shutdown();
            jda.awaitShutdown();
            System.out.println("Bot has been shut down");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Bot shutdown was interrupted");
        } finally {
            jda = null;
        }
    }

    /**
        Returns true if the bot is currently running.
    */

    public boolean isRunning() {
        return jda != null;
    }

    /**
        Returns the active JDA instance, or null if the bot is not running.
    */

    public JDA getJda() {
        return jda;
    }
}