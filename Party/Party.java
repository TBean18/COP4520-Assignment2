package party;

public class Party {
    // Development Notes:
    /*
     * The party problem is similar to the prisoner problem disussed in class
     * One Thread will be the designated CupCake replacer, All others will request a
     * new CupCake
     * The Designated replacer must keep track of the Number of CupCakes Eaten
     * Once the numEaten == numThreads we know for sure all threads have entered the
     * maze
     * 
     */
    private static int NUM_THREADS = 10;

    public static void main(String[] args) {

        Guests g = new Guests(NUM_THREADS);
        startAndWaitForThreads(g);
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
}
