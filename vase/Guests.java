package vase;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import vase.GQueue.QNode;

public class Guests implements Runnable {

    GQueue guestQueue = new GQueue();
    private AtomicBoolean complete = new AtomicBoolean(false);
    private AtomicInteger numComplete = new AtomicInteger(0);
    // Game starts with a cupcake at the exit

    @Override
    public void run() {
        int id = Integer.parseInt(Thread.currentThread().getName());

        // Each Thread will start having not seen the vase
        boolean seenVase = false;
        while (!complete.get()) {
            enterVaseRoom(id, seenVase);
        }

    }

    // Returns: true if a cupcake is eaten, otherwise false;
    public void enterVaseRoom(int id, boolean seenVase) {
        // Only one thread can enter at a time (Mutual Exclusion)

        QNode ticket = guestQueue.lock(id);

        try {

            if (!seenVase) {

                if (numComplete.incrementAndGet() >= Vase.NUM_THREADS) {
                    complete.set(true);
                }
            }
        } finally {
            guestQueue.unlock(ticket);
        }

    }
}
