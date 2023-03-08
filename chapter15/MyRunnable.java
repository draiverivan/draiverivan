public class MyRunnable implements Runnable {

    /*
     * Runnable has only one method to implement: public void run() (with no
     * arguments). This is where you put the JOB the thread is supposed to run. This
     * is the method that goes at the bottom of the new stack.
     */
    public void run() {
      go();
    }
  
    public void go() {
      /*
       * Calling sleep here will force the new thread to leave the currently-running
       * state!
       */
      try {
          Thread.sleep(2000);
      } catch (InterruptedException ex) {
          ex.printStackTrace();
      }
      /*
       * The main thread will become the currently-running thread again, and print out
       * “back in main”. Then there will be a pause (for about two seconds) before we
       * get to this line, which calls doMore() and prints out “top o’ the stack”
       */
      doMore();
    }
  
    public void doMore() {
      System.out.println("top o' the stack");
    }
  }