package de.haw.bs.p3.smoker;

import java.util.Random;

/**
 * Available ingredients
 *
 * @category de.haw.bs.p3.smoker
 */
public enum Ingredient {
    Tobacco, Paper, Matches;

    public static Ingredient getRandom(Ingredient exclude) {
        Random rand = new Random();

        Ingredient returnValue;
        do {
            returnValue = values()[rand.nextInt(values().length)];
        } while(returnValue.equals(exclude));

        return returnValue;
    }

    public static Ingredient getRandom() {
        return getRandom(null);
    }
}
