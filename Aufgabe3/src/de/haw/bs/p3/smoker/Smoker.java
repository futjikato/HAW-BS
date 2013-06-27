package de.haw.bs.p3.smoker;

import java.util.Collection;
import java.util.HashMap;

/**
 * Smoker has an infinite number of one ingredient.
 *
 * @category de.haw.bs.p3.smoker
 */
public class Smoker extends Thread {

    private Ingredient availableIngredient;

    private Agent agent;

    private int number;

    public Smoker(int number, Agent agent, Ingredient availableIngredient) {
        this.availableIngredient = availableIngredient;
        this.agent = agent;
        this.number = number;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                synchronized (agent) {
                    agent.wait();

                    Collection<Ingredient> availableIngredients = agent.getAvailableIngredients();
                    if(!availableIngredients.contains(availableIngredient)) {
                        // consume
                        System.out.println(String.format("%s consumes ingredients and smokes a cigarette.", this));
                        sleep(500);

                        agent.notify();
                    } else {
                        System.out.println(String.format("%s is unable to use the available ingredients.", this));
                    }
                }

            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println(String.format("%s has finished.", this));
    }

    @Override
    public String toString() {
        return String.format("Smoker No.%d ( has %s )", number, availableIngredient);
    }
}
