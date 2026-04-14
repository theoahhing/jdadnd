/**
    InitiativeService handles initiative rolling for players at the start of an encounter.

    Initiative is determined by rolling a d20 and adding the player's character agility modifier.
    Players are then sorted from highest initiative to lowest.
*/

package com.bktvsolutions.service;

import com.bktvsolutions.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
    Provides initiative rolling and sorting for encounter turn order.
*/

public class InitiativeService {
    private final DiceService diceService;

    /**
        Creates an initiative service with a default dice service.
    */

    public InitiativeService() {
        this.diceService = new DiceService();
    }

    /**
        Creates an initiative service with the provided dice service.

        @param diceService dice service to use
        @throws IllegalArgumentException if diceService is null
    */

    public InitiativeService(DiceService diceService) {
        if (diceService == null) {
            throw new IllegalArgumentException("diceService cannot be null");
        }

        this.diceService = diceService;
    }

    /**
        Rolls initiative for all players and returns them sorted from highest to lowest.

        @param players players participating in the encounter
        @return sorted player list in initiative order
        @throws IllegalArgumentException if players is null or empty
        @throws IllegalArgumentException if any player is null
        @throws IllegalStateException if any player has no assigned character
    */

    public List<Player> rollInitiative(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("players cannot be null or empty");
        }

        List<InitiativeEntry> initiativeEntries = new ArrayList<>();

        for (Player player : players) {
            if (player == null) {
                throw new IllegalArgumentException("player cannot be null");
            }

            if (!player.hasCharacter()) {
                throw new IllegalStateException("All players must have a character before initiative can be rolled");
            }

            int roll = diceService.rollD20();
            int modifier = player.getCharacter().getAgilityModifier();
            int total = roll + modifier;

            initiativeEntries.add(new InitiativeEntry(player, roll, modifier, total));
        }

        initiativeEntries.sort(Comparator.comparingInt(InitiativeEntry::getTotal).reversed());

        List<Player> sortedPlayers = new ArrayList<>();

        for (InitiativeEntry entry : initiativeEntries) {
            sortedPlayers.add(entry.getPlayer());
        }

        return sortedPlayers;
    }

    /**
        Rolls initiative for all players and returns detailed initiative results.

        @param players players participating in the encounter
        @return sorted initiative entries from highest to lowest
        @throws IllegalArgumentException if players is null or empty
        @throws IllegalArgumentException if any player is null
        @throws IllegalStateException if any player has no assigned character
    */

    public List<InitiativeEntry> rollInitiativeWithDetails(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("players cannot be null or empty");
        }

        List<InitiativeEntry> initiativeEntries = new ArrayList<>();

        for (Player player : players) {
            if (player == null) {
                throw new IllegalArgumentException("player cannot be null");
            }

            if (!player.hasCharacter()) {
                throw new IllegalStateException("All players must have a character before initiative can be rolled");
            }

            int roll = diceService.rollD20();
            int modifier = player.getCharacter().getAgilityModifier();
            int total = roll + modifier;

            initiativeEntries.add(new InitiativeEntry(player, roll, modifier, total));
        }

        initiativeEntries.sort(Comparator.comparingInt(InitiativeEntry::getTotal).reversed());

        return initiativeEntries;
    }

    /**
        InitiativeEntry stores the result of a single player's initiative roll.
    */

    public static class InitiativeEntry {
        private final Player player;
        private final int roll;
        private final int modifier;
        private final int total;

        /**
            Creates an initiative entry.

            @param player player associated with the roll
            @param roll raw d20 roll
            @param modifier agility modifier applied to the roll
            @param total final initiative total
        */

        public InitiativeEntry(Player player, int roll, int modifier, int total) {
            if (player == null) {
                throw new IllegalArgumentException("player cannot be null");
            }

            this.player = player;
            this.roll = roll;
            this.modifier = modifier;
            this.total = total;
        }

        public Player getPlayer() {
            return player;
        }

        public int getRoll() {
            return roll;
        }

        public int getModifier() {
            return modifier;
        }

        public int getTotal() {
            return total;
        }
    }
}