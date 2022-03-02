package vase;

public class Vase {
    public static final int NUM_THREADS = 10;
    public static final boolean IS_VERBOSE = true;

    public static void main(String[] args) {

        Guests g = new Guests();
        startAndWaitForThreads(g);
        System.out.printf("All %d guests have visted the showroom and seen the vase%n", NUM_THREADS);
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
