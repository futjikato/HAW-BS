package de.haw.bs.p3.smokerLocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.bs.p3.smoker
 */
public class Agent extends Thread {

    private Ingredient availableIngredientNo1;

    private Ingredient availableIngredientNo2;

    private List<Smoker> smokers;
    
    private Lock lock;
    
    private Condition condition;

    public void addSmokers(List<Smoker> smokers, Lock lock, Condition condition) {
        this.smokers = smokers;
        this.condition = condition;
        this.lock = lock;
    }

    @Override
    public void run() {
        refresh();

        while(!isInterrupted()) {
        	lock.lock();
            try {
                condition.signalAll();
                condition.await();

                // refresh ingredients
                refresh();
            } catch (InterruptedException e) {
                interrupt();
            } finally {
            	lock.unlock();
            }
        }

        System.out.println("Agent has finished");
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
