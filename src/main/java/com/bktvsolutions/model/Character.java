/**
    Character class provides character information such as name, level, health,
    and combat stats.
*/

package com.bktvsolutions.model;

public class Character {
    private final String id;
    private String name;

    private int level;

    private int maxHealth;
    private int currentHealth;

    private int strength;
    private int agility;
    private int intelligence;

    /**
        Creates a character with the provided base stats.

        @param id unique character identifier
        @param name character name
        @param level character level
        @param maxHealth maximum health
        @param strength strength stat
        @param agility agility stat
        @param intelligence intelligence stat
    */

    public Character(String id, String name, int level, int maxHealth, int strength, int agility, int intelligence) {
        validateString(id, "id");
        validateString(name, "name");
        validatePositive(level, "level");
        validatePositive(maxHealth, "maxHealth");
        validateNonNegative(strength, "strength");
        validateNonNegative(agility, "agility");
        validateNonNegative(intelligence, "intelligence");

        this.id = id;
        this.name = name;
        this.level = level;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateString(name, "name");
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        validatePositive(level, "level");
        this.level = level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        validatePositive(maxHealth, "maxHealth");
        this.maxHealth = maxHealth;

        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0 || currentHealth > maxHealth) {
            throw new IllegalArgumentException("currentHealth must be between 0 and maxHealth");
        }

        this.currentHealth = currentHealth;
    }

    public int getStrength() {
        return strength;
    }

    public int getStrengthModifier() {
        return (strength - 10) / 2;
    }

    public void setStrength(int strength) {
        validateNonNegative(strength, "strength");
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getAgilityModifier() {
        return (agility - 10) / 2;
    }

    public void setAgility(int agility) {
        validateNonNegative(agility, "agility");
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getIntelligenceModifier() {
        return (intelligence - 10) / 2;
    }

    public void setIntelligence(int intelligence) {
        validateNonNegative(intelligence, "intelligence");
        this.intelligence = intelligence;
    }

    /**
        Applies damage to the character.

        @param amount damage amount
    */

    public void takeDamage(int amount) {
        validateNonNegative(amount, "amount");
        currentHealth = Math.max(0, currentHealth - amount);
    }

    /**
        Heals the character without exceeding max health.

        @param amount healing amount
    */

    public void heal(int amount) {
        validateNonNegative(amount, "amount");
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }

    /**
        Returns whether the character is still alive.

        @return true if current health is above 0
    */

    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
        Fully restores the character's health.
    */

    public void restoreToFullHealth() {
        currentHealth = maxHealth;
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }

    private void validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0");
        }
    }

    private void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }

    @Override
    public String toString() {
        return "Character{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", currentHealth=" + currentHealth +
                "/" + maxHealth +
                ", strength=" + strength +
                ", agility=" + agility +
                ", intelligence=" + intelligence +
                '}';
    }
}