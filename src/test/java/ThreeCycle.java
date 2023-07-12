import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeCycle implements Runnable{
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();



    static int count = 30;

    public ThreeCycle(String name, int id) {
        this.name = name;
        this.id = id;
    }

    String name;
    int id;

    @Override
    public void run() {
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(2);
        Executors.newScheduledThreadPool(2);
        while(count > 0){
            lock.lock();
            if(count % 3 == this.id){
                System.out.println(this.name);
                condition.signalAll();
                count--;
            }


            try {
                // Causes the current thread to wait until it is signalled or interrupted.
                condition.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Thread a = new Thread(new ThreeCycle("A", 0));
        a.setDaemon(true);
        new Thread(new ThreeCycle("A",0)).start();
        new Thread(new ThreeCycle("B",2)).start();
        new Thread(new ThreeCycle("C",1)).start();

    }
}
