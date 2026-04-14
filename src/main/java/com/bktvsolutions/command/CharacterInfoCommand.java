/**
    CharacterInfoCommand displays the current player's assigned character information.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.model.Character;
import com.bktvsolutions.model.GameSession;
import com.bktvsolutions.model.Player;
import com.bktvsolutions.service.GameSessionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
    Shows the current player's character details.
*/

public class CharacterInfoCommand implements Command {
    private final GameSessionService gameSessionService;

    /**
        Creates a character info command with the provided session service.

        @param gameSessionService session service to use
        @throws IllegalArgumentException if gameSessionService is null
    */

    public CharacterInfoCommand(GameSessionService gameSessionService) {
        if (gameSessionService == null) {
            throw new IllegalArgumentException("gameSessionService cannot be null");
        }

        this.gameSessionService = gameSessionService;
    }

    /**
        Gets the name of the command as used in Discord.

        @return command name
    */

    @Override
    public String getName() {
        return "characterinfo";
    }

    /**
        Executes the character info command.

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

        long guildId = event.getGuild().getIdLong();
        GameSession session = gameSessionService.getSession(guildId);

        if (session == null) {
            event.reply("No active game session. Use /startgame first.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        String userId = event.getUser().getId();
        Player matchedPlayer = null;

        for (Player player : session.getPlayers()) {
            if (player.getId().equals(userId)) {
                matchedPlayer = player;
                break;
            }
        }

        if (matchedPlayer == null) {
            event.reply("You are not currently in the game.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (!matchedPlayer.hasCharacter()) {
            event.reply("You do not have a character assigned yet. Use /createcharacter first.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        Character character = matchedPlayer.getCharacter();

        StringBuilder response = new StringBuilder();
        response.append("Character Info:\n")
                .append("Name: ").append(character.getName()).append("\n")
                .append("Class: ").append(character.getCharacterClass()).append("\n")
                .append("Level: ").append(character.getLevel()).append("\n")
                .append("Health: ").append(character.getCurrentHealth())
                .append("/").append(character.getMaxHealth()).append("\n")
                .append("Strength: ").append(character.getStrength())
                .append(" (").append(formatModifier(character.getStrengthModifier())).append(")\n")
                .append("Agility: ").append(character.getAgility())
                .append(" (").append(formatModifier(character.getAgilityModifier())).append(")\n")
                .append("Intelligence: ").append(character.getIntelligence())
                .append(" (").append(formatModifier(character.getIntelligenceModifier())).append(")");

        event.reply(response.toString()).queue();
    }

    private String formatModifier(int value) {
        if (value >= 0) {
            return "+" + value;
        }

        return String.valueOf(value);
    }
}