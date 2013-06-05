package de.haw.bs.p3.smokerLocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simulation
 * Creates all needed instances and starts the simulation
 *
 * @category de.haw.bs.p3.smoker
 */
public class Simulation {

    private List<Smoker> smokers = new ArrayList<Smoker>();

    private Agent agent;
    
    private Lock lock = new ReentrantLock();
    
    private Condition condition = lock.newCondition();

    public Simulation() {
        // initialize agent
        agent = new Agent();
        agent.setName("Agent-Thread");

        // add smokers
            Smoker smoker = new Smoker(1, agent, Ingredient.Matches, lock, condition);
            // smoker will immediately wait for agent to notify him
            smoker.start();
            smokers.add(smoker);

            smoker = new Smoker(2, agent, Ingredient.Paper, lock, condition);
            smoker.start();
            smokers.add(smoker);

            smoker = new Smoker(3, agent, Ingredient.Tobacco, lock, condition);
            smoker.start();
            smokers.add(smoker);

            agent.addSmokers(smokers, lock, condition);
            agent.start();

        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        agent.interrupt();

        for(Smoker cSmoker : smokers) {
            cSmoker.interrupt();
        }
    }
}
