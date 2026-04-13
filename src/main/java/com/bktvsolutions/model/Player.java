/**
    Player class maps character info to each Discord user.
*/

package com.bktvsolutions.model;

public class Player {
    private final String id;
    private final String username;

    private Character character;
    private boolean inGame;

    /**
        Creates a player with no assigned character.

        @param id unique player identifier, typically the Discord user ID
        @param username player's username
    */

    public Player(String id, String username) {
        validateString(id, "id");
        validateString(username, "username");

        this.id = id;
        this.username = username;
        this.inGame = false;
    }

    /**
        Creates a player with an assigned character.

        @param id unique player identifier
        @param username player's username
        @param character assigned character
    */

    public Player(String id, String username, Character character) {
        this(id, username);
        setCharacter(character);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        if (character == null) {
            throw new IllegalArgumentException("character cannot be null");
        }

        this.character = character;
    }

    public boolean hasCharacter() {
        return character != null;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", character=" + (character != null ? character.getName() : "none") +
                ", inGame=" + inGame +
                '}';
    }
}
