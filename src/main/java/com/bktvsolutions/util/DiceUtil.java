/**
    Dice utilities class provides helper methods for generating random numbers
    for dice rolls, including single and multiple dice within a specified range,
    without maintaining any state.
*/

package com.bktvsolutions.util;

import java.util.concurrent.ThreadLocalRandom;

public final class DiceUtil {

    /**
        Private constructor to prevent instantiation.
    */

    private DiceUtil() {
        throw new UnsupportedOperationException("DiceUtil class cannot be instantiated");
    }

    /**
        Generates a random number between the specified minimum and maximum values (inclusive).

         @param min minimum value (inclusive)
         @param max maximum value (inclusive)
         @return random number within the specified range
         @throws IllegalArgumentException if min is greater than max
    */

    public static int randomInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
         Rolls a single die with the specified number of sides.

         @param sides number of sides on the die
         @return result of the roll
         @throws IllegalArgumentException if sides is less than or equal to 0
    */

    public static int rollDie(int sides) {
        if (sides <= 0) {
            throw new IllegalArgumentException("sides must be greater than 0");
        }

        return randomInRange(1, sides);
    }

    /**
         Rolls multiple dice with the specified number of sides.

         @param count number of dice to roll
         @param sides number of sides on each die
         @return total result of all dice rolls
         @throws IllegalArgumentException if count or sides are less than or equal to 0
    */

    public static int rollDice(int count, int sides) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }

        if (sides <= 0) {
            throw new IllegalArgumentException("sides must be greater than 0");
        }

        int total = 0;

        for (int i = 0; i < count; i++) {
            total += rollDie(sides);
        }

        return total;
    }
}