package de.haw.bs.p3.mensa;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Student
 *
 * @category de.haw.bs.p3.mensa
 */
public class Student extends Thread {

    private List<Till> tills;

    private Semaphore semaphore;

    private int number;

    /**
     * Constructs a new student
     * The thread does not start automatically.
     * At least one till have to be in the list. Otherwise an SimulationException will be thrown.
     *
     * @param tills
     * @param semaphore
     * @throws SimulationException
     */
    public Student(int number, List<Till> tills, Semaphore semaphore) throws SimulationException {
        if(tills.size() < 1)
            throw new SimulationException("The mensa needs to have at least one till.");

        this.number = number;
        this.tills = tills;
        this.semaphore = semaphore;
    }

    /**
     * Spin the student in the mensa checkout process
     * Every time he leaves the till´s queue
     */
    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                // acquire slot to check for shortest queue
                semaphore.acquire();

                // check queue for all tills
                Till shortestQueu = tills.get(0);
                for (Till checkTill : tills) {
                    if(checkTill.getQueueSize() < shortestQueu.getQueueSize()) {
                        shortestQueu = checkTill;
                    }
                }

                // go into queue
                shortestQueu.add(this);

                // release queue
                semaphore.release();

                // get in line at shortest queue
                shortestQueu.pay(this);

                // eating takes at least 200 ms and up to 700 ms
                long time = 200 + (long)(Math.random() * 500);
                Thread.currentThread().sleep(time);

            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Student No.%d", number);
    }
}
