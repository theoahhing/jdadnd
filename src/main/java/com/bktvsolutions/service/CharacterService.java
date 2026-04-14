/**
    CharacterService provides character creation logic and assigns default stats
    based on the selected character class.
*/

package com.bktvsolutions.service;

import com.bktvsolutions.model.Character;
import com.bktvsolutions.model.CharacterClass;

/**
    Handles character creation and class-based defaults.
*/

public class CharacterService {

    /**
        Creates a character using class-based default stats.

        @param id unique character identifier
        @param name character name
        @param characterClass selected character class
        @return created character
        @throws IllegalArgumentException if id or name is invalid
        @throws IllegalArgumentException if characterClass is null
    */

    public Character createCharacter(String id, String name, CharacterClass characterClass) {
        validateString(id, "id");
        validateString(name, "name");

        if (characterClass == null) {
            throw new IllegalArgumentException("characterClass cannot be null");
        }

        switch (characterClass) {
            case FIGHTER:
                return new Character(id, name, characterClass, 1, 20, 14, 10, 8);

            case WIZARD:
                return new Character(id, name, characterClass, 1, 12, 8, 10, 14);

            case ASSASSIN:
                return new Character(id, name, characterClass, 1, 16, 10, 14, 10);

            default:
                throw new IllegalArgumentException("Unsupported character class: " + characterClass);
        }
    }

    /**
        Parses a user-provided class name into a CharacterClass.

        @param value class name provided by user
        @return parsed CharacterClass
        @throws IllegalArgumentException if value is null, blank, or unsupported
    */

    public CharacterClass parseCharacterClass(String value) {
        validateString(value, "value");

        String normalizedValue = value.trim().toUpperCase();

        try {
            return CharacterClass.valueOf(normalizedValue);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid character class: " + value);
        }
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }
}