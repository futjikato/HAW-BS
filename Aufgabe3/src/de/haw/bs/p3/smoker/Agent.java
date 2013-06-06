package de.haw.bs.p3.smoker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.bs.p3.smoker
 */
public class Agent extends Thread {

    private Ingredient availableIngredientNo1;

    private Ingredient availableIngredientNo2;

    @Override
    public void run() {
        refresh();

        while(!isInterrupted()) {
            try {
                synchronized (this) {
                    notifyAll();
                    wait();
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public Collection<Ingredient> getAvailableIngredients() {
        List<Ingredient> availableIngredients = new ArrayList<Ingredient>();
        availableIngredients.add(availableIngredientNo1);
        availableIngredients.add(availableIngredientNo2);

        return availableIngredients;
    }

    public void refresh() {
        availableIngredientNo1 = Ingredient.getRandom();
        availableIngredientNo2 = Ingredient.getRandom(availableIngredientNo1);
        System.out.println(String.format("Agent has new supplies : %s, %s", availableIngredientNo1, availableIngredientNo2));
    }
}
