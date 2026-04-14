/**
    Configuration class provides access to environment variables required
    by the application, such as the Discord bot token.
*/

package com.bktvsolutions.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
    Loads and provides application configuration values.
*/

public final class Config {
    private static final Dotenv DOTENV = Dotenv.load();

    private Config() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
        Returns the Discord bot token from the .env file.

        @throws IllegalStateException if DISCORD_TOKEN is missing or blank
    */

    public static String getDiscordToken() {
        String token = DOTENV.get("DISCORD_TOKEN");

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("DISCORD_TOKEN is not set in the .env file");
        }

        return token;
    }

    /**
        Returns the Discord Guild ID from the .env file.
    */

    public static String getDiscordGuildId() {
        String guildId = DOTENV.get("DISCORD_GUILD_ID");

        if (guildId == null || guildId.trim().isEmpty()) {
            throw new IllegalStateException("DISCORD_GUILD_ID is not set");
        }

        return guildId;
    }
}