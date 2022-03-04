package vase;

public class Vase {
    public static final int NUM_THREADS = 10;
    public static final boolean IS_VERBOSE = false;

    static class Timer {

        long start, end, dur;

        void start() {
            start = System.currentTimeMillis();
        }

        long end() {

            end = System.currentTimeMillis();
            dur = end - start;
            return (dur);
        }
    }

    public static void main(String[] args) {

        Timer t = new Timer();
        t.start();

        Guests g = new Guests();
        startAndWaitForThreads(g);
        t.end();
        System.out.printf("All %d guests have visited the showroom and seen the vase%n", NUM_THREADS);
        System.out.printf("Computation Completed in %dms", t.dur);

    }

    public static void startAndWaitForThreads(Runnable r) {
        // Spawn NUM_THREADS Threads
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(r, "" + i);
            threads[i].start();
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface logInterface {
        void printStatement();
    }

    public static void log(logInterface lInterface) {

        if (Vase.IS_VERBOSE) {
            lInterface.printStatement();
        }
    }

}
