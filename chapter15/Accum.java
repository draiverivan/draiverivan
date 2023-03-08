/* Threads from two different classes are updating 
the same object in a third class, because both 
threads are accessing a single instance of Accum. 
The line of code: 
private static Accum a = new Accum( ); creates a 
static instance of Accum (remember static means 
one per class), and the private constructor in 
Accum means that no one else can make an Accum 
object. These two techniques (private constructor 
and static getter method) used together, create 
what’s known as a ‘Singleton’ - an OO pattern to 
restrict the number of instances of an object 
that can exist in an application. (Usually, there’s 
just a single instance of a Singleton—hence the 
name), but you can use the pattern to restrict the 
instance creation in whatever way you choose.) */

class Accum {
    private static Accum a = new Accum(); // create a static instance of class Accum
    private int counter = 0;

    private Accum() {
    } // A private constructor

    public static Accum getAccum() {
        return a;
    }

    public void updateCounter(int add) {
        counter += add;
    }

    public int getCount() {
        return counter;
    }
}

class ThreadOne implements Runnable {
    Accum a = Accum.getAccum();

    public void run() {
        for (int x = 0; x < 98; x++) {
            a.updateCounter(1000);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("one " + a.getCount());
    }
}