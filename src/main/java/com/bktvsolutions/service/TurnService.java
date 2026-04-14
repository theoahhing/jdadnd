/**
    TurnService manages turn order, round progression, and active player tracking.

    It does not store players permanently, only references them during an encounter.
*/

package com.bktvsolutions.service;

import com.bktvsolutions.model.Player;

import java.util.List;

public class TurnService {

    private List<Player> players;
    private int currentTurnIndex;
    private int roundNumber;
    private boolean active;

    /**
        Initializes a new encounter.

        @param players list of players in turn order
    */

    public void startEncounter(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Players cannot be null or empty");
        }

        this.players = players;
        this.currentTurnIndex = 0;
        this.roundNumber = 1;
        this.active = true;
    }

    /**
        Advances to the next player's turn.
    */

    public void nextTurn() {
        if (!active) {
            throw new IllegalStateException("No active encounter");
        }

        currentTurnIndex++;

        if (currentTurnIndex >= players.size()) {
            currentTurnIndex = 0;
            roundNumber++;
        }
    }

    /**
        Returns the current player.
    */

    public Player getCurrentPlayer() {
        if (!active) {
            throw new IllegalStateException("No active encounter");
        }

        return players.get(currentTurnIndex);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean isActive() {
        return active;
    }

    /**
        Ends the current encounter.
    */

    public void endEncounter() {
        active = false;
        players = null;
    }
}