public class RyanAndMonicaJob implements Runnable {
    private BankAccount account = new BankAccount();

    public static void main(String[] args) {

        /*
         * There will be only ONE instance of the
         * RyanAndMonicaJob.That means only
         * ONE instance of the bank account. Both
         * threads will access this one account
         */
        RyanAndMonicaJob theJob = new RyanAndMonicaJob(); /* instantiate the Runnable (job) */

        /*
         * Make two threads, giving each thread the same Runnable
         * job. That means both threads will be accessing the one
         * account instance variable in the Runnable class.
         */
        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);
        one.setName("Ryan");
        two.setName("Monica");
        one.start();
        two.start();
    }

    /*
     * In the run() method, a thread loops through and tries
     * to make a withdrawal with each iteration. After the
     * withdrawal, it checks the balance once again to see if
     * the account is overdrawn.
     */
    public void run() {
        for (int x = 0; x < 10; x++) {
            makeWithdrawal(10);
            if (account.getBalance() < 0) {
                System.out.println("Overdrawn!");
            }
        }
    }

    private synchronized void makeWithdrawal(int amount) {

        /*
         * Check the account balance, and if there’s not
         * enough money, we just print a message. If there IS
         * enough, we go to sleep, then wake up and complete
         * the withdrawal, just like Ryan did.
         */
        if (account.getBalance() >= amount) {
            System.out.println(Thread.currentThread().getName() + " is about to withdraw");
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " woke up.");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " completes the withdrawal");
        } else {
            System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
        }
    }
}
