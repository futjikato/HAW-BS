package de.haw.bs.p3.smokerLocks;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Smoker has an infinite number of one ingredient.
 *
 * @category de.haw.bs.p3.smoker
 */
public class Smoker extends Thread {

    private Ingredient availableIngredient;

    private Agent agent;

    private int number;
    
    private Lock lock;
    
    private Condition condition;

    public Smoker(int number, Agent agent, Ingredient availableIngredient, Lock lock, Condition condition) {
        this.availableIngredient = availableIngredient;
        this.agent = agent;
        this.number = number;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
        	lock.lock();
            try {
                condition.await();

                Collection<Ingredient> availableIngredients = agent.getAvailableIngredients();
                if(!availableIngredients.contains(availableIngredient)) {
                    // consume
                    System.out.println(String.format("%s consumes ingredients and smokes a cigarette.", this));
                    sleep(500);

                    condition.signal();
                } else {
                    System.out.println(String.format("%s is unable to use the available ingredients.", this));
                }

            } catch (InterruptedException e) {
                interrupt();
            } finally {
            	lock.unlock();
            }
        }

        System.out.println(String.format("%s has finished.", this));
    }

    @Override
    public String toString() {
        return String.format("Smoker No.%d", number);
    }
}
