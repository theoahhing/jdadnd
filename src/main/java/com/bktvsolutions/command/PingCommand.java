/**
    Ping command provides a simple test implementation that responds with "Pong!" to verify
    that the bot is online and handling slash command interactions correctly.
*/

package com.bktvsolutions.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
    A simple command that replies with "Pong!".
*/

public class PingCommand  extends ListenerAdapter implements Command {

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
        event.reply("Boyd heuele la caca!").queue();
    }
}