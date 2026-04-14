/**
    GameSession represents a single DND game instance within a Discord guild or channel.

    It stores players, game state, and the turn service responsible for managing turns.
*/

package com.bktvsolutions.model;

import com.bktvsolutions.service.TurnService;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private final List<Player> players;
    private final TurnService turnService;

    private boolean active;

    public GameSession() {
        this.players = new ArrayList<>();
        this.turnService = new TurnService();
        this.active = false;
    }

    /**
        Adds a player to the session.

        @param player player to add
    */

    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
        Returns list of players in the session.
    */

    public List<Player> getPlayers() {
        return players;
    }

    /**
        Starts the game session.
    */

    public void start() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot start game with no players");
        }

        active = true;
        turnService.startEncounter(players);
    }

    /**
        Starts the game session using the provided player order.

        @param playerOrder ordered players for turn sequence
        @throws IllegalArgumentException if playerOrder is null or empty
    */

    public void start(List<Player> playerOrder) {
        if (playerOrder == null || playerOrder.isEmpty()) {
            throw new IllegalArgumentException("Player Order cannot be null or empty");
        }

        active = true;
        turnService.startEncounter(playerOrder);
    }

    /**
        Ends the game session.
    */

    public void end() {
        active = false;
        turnService.endEncounter();
    }

    public boolean isActive() {
        return active;
    }

    public TurnService getTurnService() {
        return turnService;
    }
}