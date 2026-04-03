/**
    Dice service provides higher-level dice rolling operations for game logic,
    including single-die and multiple-dice rolls, without maintaining any state.

    Later implementations:
        - Roll formatting
        - Modifiers
        - Adv/Dis
        - Crit checks
*/

package com.bktvsolutions.service;

import com.bktvsolutions.util.DiceUtil;

public class DiceService {

    /**
       Rolls a single die with the specified number of sides.

       @param sides number of sides on the die
       @return result of the die roll
       @throws IllegalArgumentException if sides is less than or equal to 0
    */

    public int rollDie(int sides) {
        return DiceUtil.rollDie(sides);
    }

    /**
        Rolls multiple dice with the specified number of sides.

        @param count number of dice to roll
        @param sides number of sides on each die
        @return total of all dice rolls
        @throws IllegalArgumentException if count or sides are less than or equal to 0
    */

    public int rollDice(int count, int sides) {
        return DiceUtil.rollDice(count, sides);
    }

    /**
        Rolls a standard twenty-sided die.

        @return result of the d20 roll
    */

    public int rollD20() {
        return rollDie(20);
    }

    /**
        Rolls a standard six-sided die.

        @return result of the d6 roll
    */

    public int rollD6() {
        return rollDie(6);
    }
}