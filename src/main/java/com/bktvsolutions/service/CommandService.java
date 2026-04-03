/**
    Command service coordinates the execution of slash commands by delegating incoming events
    to the appropriate command using the command manager, and handling unknown command cases.
*/

package com.bktvsolutions.service;

import com.bktvsolutions.command.Command;
import com.bktvsolutions.command.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
    Handles slash command execution for the bot.
*/

public class CommandService {
    private final CommandManager commandManager;

    /**
        Creates a command service with a default command manager.
    */

    public CommandService() {
        this.commandManager = new CommandManager();
    }

    /**
        Creates a command service with the provided command manager.

        @param commandManager command manager to use
        @throws IllegalArgumentException if commandManager is null
    */

    public CommandService(CommandManager commandManager) {
        if (commandManager == null) {
            throw new IllegalArgumentException("commandManager cannot be null");
        }

        this.commandManager = commandManager;
    }

    /**
        Executes the matching slash command.

        @param event slash command interaction event
    */

    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        String commandName = event.getName();
        Command command = commandManager.getCommand(commandName);

        if (command != null) {
            command.execute(event);
            return;
        }

        handleUnknown(event);
    }

    /**
        Handles unknown commands.

        @param event slash command interaction event
    */

    private void handleUnknown(SlashCommandInteractionEvent event) {
        event.reply("Unknown command")
                .setEphemeral(true)
                .queue();
    }
}