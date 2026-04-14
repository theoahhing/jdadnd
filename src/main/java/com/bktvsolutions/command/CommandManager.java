/**
    Command manager provides storage and registration of all available bot commands, allowing
    retrieval and lookup by command name for execution handling.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.service.GameSessionService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
    Stores and manages the bot's available commands.
*/

public class CommandManager {
    private final Map<String, Command> commands;
    private final GameSessionService gameSessionService;

    /**
        Creates a command manager and registers default commands.
    */

    public CommandManager() {
        this.commands = new HashMap<>();
        this.gameSessionService = new GameSessionService();

        registerDefaultCommands();
    }

    /**
        Registers the default commands used by the bot.
    */

    private void registerDefaultCommands() {
        registerCommand(new PingCommand());
        registerCommand(new RollCommand());
        registerCommand(new StartGameCommand(gameSessionService));
        registerCommand(new JoinGameCommand(gameSessionService));
        registerCommand(new CreateCharacterCommand(gameSessionService));
        registerCommand(new StartEncounterCommand(gameSessionService));
        registerCommand(new EndTurnCommand(gameSessionService));
        registerCommand(new EndGameCommand(gameSessionService));
    }

    /**
        Registers a command with the manager.

        @param command command to register
        @throws IllegalArgumentException if command is null
    */

    public void registerCommand(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("command cannot be null");
        }

        commands.put(command.getName(), command);
    }

    /**
        Gets a command by its name.

        @param name command name
        @return matching command, or null if not found
    */

    public Command getCommand(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return commands.get(name);
    }

    /**
        Checks whether a command exists.

        @param name command name
        @return true if the command exists, otherwise false
    */

    public boolean hasCommand(String name) {
        return getCommand(name) != null;
    }

    /**
        Gets all registered commands.

        @return unmodifiable collection of registered commands
    */

    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(commands.values());
    }
}