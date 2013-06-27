package de.haw.bs.p3.mensa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Till object used by students in checkout process
 *
 * @category de.haw.bs.p3.mensa
 */
public class Till {

    private List<Student> waitingLine = new ArrayList<Student>();

    private Semaphore semaphore = new Semaphore(1, true);

    private int number;

    public Till(int number) {
        this.number = number;
    }

    /**
     * Returns the current queue size
     *
     * @return queue size
     */
    public int getQueueSize() {
        return waitingLine.size();
    }

    /**
     * Add the student to the waiting queue
     *
     * @param student
     */
    public void add(Student student) {
        // add student to queue
        waitingLine.add(student);
    }

    /**
     * Process payment
     * Blocks if payment is blocked by other persons in line
     *
     * @param student
     * @throws InterruptedException
     */
    public void pay(Student student) throws InterruptedException {
        // acquire payment semaphore
        semaphore.acquire();

        try {
            // payment takes up to 100 ms
            long time = (long)(Math.random() * 100);
            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // print info
        System.out.println(String.format("%s has payed at till no.%d with %d students in queue", student, number, getQueueSize()));

        // remove from queue after payment
        waitingLine.remove(student);

        // release payment slot
        semaphore.release();
    }
}
