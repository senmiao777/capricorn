package concurrent;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {


    @Test
    public void testPark() {
        Thread t = new Thread(() -> {
            LockSupport.park();
            System.out.println("---子线程执行----");
        });
        t.start();
        System.out.println("---main线程执行---");
        LockSupport.unpark(t);
    }

    @Test
    public void testPartestPark2k2() {
        Thread t = new Thread(() -> {
            LockSupport.unpark(Thread.currentThread());
            LockSupport.park();
            System.out.println("---子线程执行----");
        });
        t.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---main线程执行---");

    }
}
