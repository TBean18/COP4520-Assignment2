package vase;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import party.Party;
import vase.GQueue.QNode;

public class Guests implements Runnable {

    Lock mazeInvitation = new ReentrantLock(true);
    GQueue guestQueue = new GQueue();
    private AtomicBoolean complete = new AtomicBoolean(false);
    private AtomicInteger numComplete = new AtomicInteger(0);
    // Game starts with a cupcake at the exit
    private boolean isCupcake = true;
    private int numConfirmed = 0;
    private final static int N = Vase.NUM_THREADS;

    @Override
    public void run() {
        int id = Integer.parseInt(Thread.currentThread().getName());
        // Everyone wants to be the eater, but there can be only one ☜(ﾟヮﾟ☜)
        // Here we will assume it is alway thread id = 0
        // Each thread will only eat one cupcake
        boolean seenVase = false;
        while (!complete.get()) {
            enterVaseRoom(id, seenVase);
        }

    }

    // Returns: true if a cupcake is eaten, otherwise false;
    public void enterVaseRoom(int id, boolean seenVase) {
        // Only one thread can enter at a time (Mutual Exclusion)
        // Only one guest will be invited to the maze at a time
        // Synchronized has no fairness policy

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
