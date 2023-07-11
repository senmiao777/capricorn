import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeCycle2 implements Runnable {
    static Object lock = new Object();


    static int count = 30;

    public ThreeCycle2(String name, int id) {
        this.name = name;
        this.id = id;
    }

    String name;
    int id;

    @Override
    public void run() {

        while (count > 0) {
            synchronized (lock) {
                if (count % 3 == this.id) {
                    System.out.println(this.name);

                    lock.notifyAll();

                    count--;
                }


                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new ThreeCycle2("A", 0)).start();
        new Thread(new ThreeCycle2("B", 2)).start();
        new Thread(new ThreeCycle2("C", 1)).start();

    }
}
