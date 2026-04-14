/**
    CreateCharacterCommand creates and assigns a character to the player in the current game session.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.Character;
import com.bktvsolutions.model.CharacterClass;
import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.CharacterService;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CreateCharacterCommand implements Command {
    private final GameSessionService gameSessionService;
    private final CharacterService characterService;

    /**
        Creates a create character command with the provided game session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public CreateCharacterCommand(GameSessionService gameSessionService) {
        if (gameSessionService == null) {
            throw new IllegalArgumentException("gameSessionService cannot be null");
        }

        this.gameSessionService = gameSessionService;
        this.characterService = new CharacterService();
    }

    /**
        Creates a create character command with the provided services.

        @param gameSessionService session service to use
        @param characterService character service to use
        @throws IllegalArgumentException if either dependency is null
    */

    public CreateCharacterCommand(GameSessionService gameSessionService, CharacterService characterService) {
        if (gameSessionService == null) {
            throw new IllegalArgumentException("gameSessionService cannot be null");
        }

        if (characterService == null) {
            throw new IllegalArgumentException("characterService cannot be null");
        }

        this.gameSessionService = gameSessionService;
        this.characterService = characterService;
    }

    /**
        Gets the name of the command as used in Discord.

        @return command name
    */

    @Override
    public String getName() {
        return "createcharacter";
    }

    /**
        Executes the create character command.

        @param event slash command interaction event
    */

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        if (event.getGuild() == null) {
            event.reply("This command can only be used in a server.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (event.getOption("name") == null || event.getOption("class") == null) {
            event.reply("You must provide both a character name and class.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        long guildId = event.getGuild().getIdLong();
        GameSession session = gameSessionService.getSession(guildId);

        if (session == null) {
            event.reply("No game session exists yet. Use /startgame first.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        String userId = event.getUser().getId();
        Player player = null;

        for (Player currentPlayer : session.getPlayers()) {
            if (currentPlayer.getId().equals(userId)) {
                player = currentPlayer;
                break;
            }
        }

        if (player == null) {
            event.reply("You must join the game before creating a character.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (player.hasCharacter()) {
            event.reply("You already have a character assigned.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        String characterName = event.getOption("name").getAsString();
        String className = event.getOption("class").getAsString();

        CharacterClass characterClass;

        try {
            characterClass = characterService.parseCharacterClass(className);
        } catch (IllegalArgumentException exception) {
            event.reply("Invalid class. Available classes: fighter, wizard, assassin.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        Character character = characterService.createCharacter(userId, characterName, characterClass);
        player.setCharacter(character);

        event.reply(player.getUsername() + " created character **" + character.getName()
                        + "** (" + character.getCharacterClass() + ").")
                .queue();
    }
}