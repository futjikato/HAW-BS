/**
 * @startuml
 * Simulation -- Till
 * Simulation -- Student
 * Simulation -- SimulationException
 *
 * Simulation:void start()
 *
 * SimulationException:
 *
 * Till:Semaphore semaphore
 * Till:List<Student> waitingLine
 * Till:int getQueueSize()
 * Till:void add(Student student)
 * Till:void pay(Student student)
 *
 * Student:List<Till> tills
 * Student:Semaphore semaphore
 * Student:void run()
 * @enduml
 */
package de.haw.bs.p3.mensa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Simulation
 *
 * @category de.haw.bs.p3.mensa
 */
public class Simulation {

    private final int runtime;

    private int checkoutCount = 0;

    private int studentCount = 0;

    public Simulation(int checkoutCount, int studentCount, int runtime) {
        this.checkoutCount = checkoutCount;
        this.studentCount = studentCount;
        this.runtime = runtime;
    }

    /**
     * Run simulation
     * First initializes tills and students.
     * Every student thread will be started.
     * Simulation will stop after the given amount of seconds.
     *
     * @throws SimulationException
     */
    public void start() throws SimulationException {

        // initialize semaphore
        Semaphore sem = new Semaphore(1);

        // initialize tills
        List<Till> tills = new ArrayList<Till>();
        for(int j = 0 ; j < checkoutCount ; j++) {
            tills.add(new Till(j+1));
        }

        // initialize students
        List<Student> students = new ArrayList<Student>();
        for(int i = 0 ; i < studentCount ; i++) {
            Student stud = new Student(i+1, tills, sem);
            stud.start();
            students.add(stud);
        }

        System.out.println("Simulation started");

        // simulate for a given time
        try {
            Thread.currentThread().sleep(this.runtime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // interrupt all students
            for(Student stud : students) {
                stud.interrupt();
                System.out.println(String.format("%s interrupted", stud));
            }
        }

        System.out.println("Simulation ended");
    }
}
