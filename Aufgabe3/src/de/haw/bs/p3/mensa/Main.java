package de.haw.bs.p3.mensa;

public class Main {

    /**
     * Amount of students to be part of the simulation.
     */
    private static final int STUDENT_COUNT = 10;

    /**
     * Amount of tills to be part of simulation.
     */
    private static final int CHECKOUT_COUNT = 2;

    /**
     * Amount of seconds the simulation should run.
     */
    private static final int RUNTIME = 2;

    public static void main(String[] args) {
        // initialize simulation
        Simulation sim = new Simulation(CHECKOUT_COUNT, STUDENT_COUNT, RUNTIME);

        try {
            sim.start();
        } catch (SimulationException e) {
            e.printStackTrace();
        }
    }
}
