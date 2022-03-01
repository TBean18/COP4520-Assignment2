package party;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Guests implements Runnable {

    Lock mazeInvitation = new ReentrantLock(true);
    private AtomicBoolean complete = new AtomicBoolean(false);
    // Game starts with a cupcake at the exit
    private boolean isCupcake = true;
    private int numConfirmed = 0;
    private int N;

    Guests(int numThreads) {
        this.N = numThreads;
    }

    @Override
    public void run() {
        int id = Integer.parseInt(Thread.currentThread().getName());
        // Everyone wants to be the eater, but there can be only one ☜(ﾟヮﾟ☜)
        // Here we will assume it is alway thread id = 0
        // Each thread will only eat one cupcake
        boolean eaten = false;
        while (!eaten && !complete.get()) {
            eaten = enterMaze(id);
        }

    }

    // Returns: true if a cupcake is eaten, otherwise false;
    public boolean enterMaze(int ID) {

        // Only one thread can enter at a time (Mutual Exclusion)
        // Only one guest will be invited to the maze at a time
        // Synchronized has no fairness policy
        mazeInvitation.lock();

        try {
            System.out.println(ID + " enters maze");
            if (ID == 0 && !isCupcake) {
                numConfirmed++;
                System.out.printf("Missing Cupcake detected numConfirmed: %d%n", numConfirmed);
                // replace the cupcake
                isCupcake = true;
                if (numConfirmed >= (N - 1)) {
                    System.out.printf("Numconfirmed (%d) >= (%d) numGuests", numConfirmed, (N - 1));
                    complete.set(true);
                    return true;
                }
                return false;

            }
            if (isCupcake && ID != 0) {
                // eat the cupcake
                isCupcake = false;
                System.out.println(ID + " eats cupcake");
                return true;
            }
            return false;
        } finally {
            mazeInvitation.unlock();
        }

    }

}
