class TestSync implements Runnable {
    private int balance;

    public void run() {
        for (int i = 0; i < 50; i++) {
            /* each thread runs 50 times, incrementing the balance on each iteration */
            increment();
            System.out.println("balance is " + balance);
        }
    }

    public void increment() {
        /*
         * Here’s the crucial part! We increment the balance by
         * adding 1 to whatever the value of balance was AT THE
         * TIME WE READ IT (rather than adding 1 to whatever
         * the CURRENTvalue is)
         */
        int i = balance;
        balance = i + 1;
    }
}
/* For example below it can be this */
/*
 * We lost the last updates
 * that Thread A made!
 * Thread B had previously
 * done a ‘read’ of the value
 * of balance, and when B
 * woke up, it just kept going
 * as if it never missed a beat.
 */

/*
 * Synchronizing the increment() method solves the “Lost
 * Update” problem, because it keeps the two steps in the method
 * as one unbreakable unit.
 */

/*
 * public synchronized void increment() {
 * int i = balance;
 * balance = i + 1;
 * }
 */

/*
 * Once a thread enters
 * the method, we have
 * to make sure that all
 * the steps in the method
 * complete (as one
 * atomic process) before
 * any other thread can
 * enter the method
 */