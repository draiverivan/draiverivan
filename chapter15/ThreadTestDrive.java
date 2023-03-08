class ThreadTestDrive {
    public static void main(String[] args) {
      Runnable threadJob = new MyRunnable();
  
      /*
       * Pass the new Runnable instance into the new Thread constructor. This tells
       * the thread what method to put on the bottom of the new stack. In other words,
       * the first method that the new thread will run.
       */
      Thread myThread = new Thread(threadJob);
      /*
       * You won’t get a new thread of execution until you call start() on the Thread
       * instance. A thread is not really a thread until you start it. Before that,
       * it’s just a Thread instance, like any other object, but it won’t have any
       * real ‘threadness’
       */
      myThread.start();
      System.out.println("back in main");
    }
  }