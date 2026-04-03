/**
    Command interface defines the structure for all bot commands, requiring a name for identification
    and an execute method to handle command logic when triggered by a Discord interaction.
*/

package com.bktvsolutions.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
    Represents a slash command that can be executed by the bot.
*/

public interface Command {

    /**
        Gets the name of the command as used in Discord.

        @return command name
    */

    String getName();

    /**
        Executes the command.

        @param event slash command interaction event
    */

    void execute(SlashCommandInteractionEvent event);
}