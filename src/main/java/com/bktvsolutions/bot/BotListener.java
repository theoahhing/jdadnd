/**
    Bot listener class handles Discord events received through JDA,
    such as reading messages and sending replies.
*/

package com.bktvsolutions.bot;

import com.bktvsolutions.command.CommandManager;
import com.bktvsolutions.service.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
    Handles Discord bot events.
*/

public class BotListener extends ListenerAdapter {
    private final CommandService commandService;

    /**
        Creates a bot listener with the provided command manager.

        @param commandManager command manager used to resolve and execute commands
        @throws IllegalArgumentException if commandManager is null
    */

    public BotListener(CommandManager commandManager) {
        if (commandManager == null) {
            throw new IllegalArgumentException("commandManager cannot be null");
        }

        this.commandService = new CommandService(commandManager);
    }

    /**
        Called whenever a message is received in a channel the bot can access.
        Replies with "Hello" when a user sends "hello".
    */

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw();
        System.out.println("Received message: " + message);

        if (message.equalsIgnoreCase("hello")) {
            event.getChannel().sendMessage("Hello").queue();
        }
    }

    /**
        Called whenever a slash command is used.

        @param event slash command interaction event
    */

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        commandService.handleSlashCommand(event);
    }
}