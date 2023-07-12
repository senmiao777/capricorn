import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class TwoCycle implements Runnable {
    private static Object lock = new Object();

    int id;
    String name;
    static int count = 5;



    public TwoCycle(String name) {
        this.name = name;
    }

    public TwoCycle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {


        while(count > 0){
            synchronized (lock){
                if(count % 2 ==id){
                    System.out.println(name);
                    lock.notifyAll();

                    count --;
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }







    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore ticket = new Semaphore(1);
        new Thread(new TwoCycle(1,"B")).start();
        new Thread(new TwoCycle(0,"C")).start();


    }
}
