/**
    Character class provides character stats and basic tracking + combat methods.
*/

package com.bktvsolutions.model;

public class Character {
    private String id;
    private String name;
    private int level;

    private int maxHealth;
    private int currentHealth;

    private int strength;
    private int agility;
    private int intelligence;

    private boolean alive;

    public void takeDamage(int amount){
        currentHealth -= amount;
        if(currentHealth <= 0){
            currentHealth = 0;
            alive = false;
        }
    }

    public void heal(int amount){
        currentHealth = Math.min(currentHealth + amount, maxHealth);
    }

    public boolean isAlive(){
        return alive;
    }



}
