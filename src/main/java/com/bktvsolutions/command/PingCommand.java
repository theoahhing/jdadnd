/**
    Ping command provides a simple test implementation that responds with a message to verify
    that the bot is online and handling slash command interactions correctly.
*/

package com.bktvsolutions.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
    A simple command that replies with a pong message.
*/

public class PingCommand implements Command {

    /**
        Returns the name of the command used in Discord.
    */

    @Override
    public String getName() {
        return "ping";
    }

    /**
        Executes the ping command.

        @param event slash command interaction event
    */

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        event.reply("Pong!").queue();
    }
}