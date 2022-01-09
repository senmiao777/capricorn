package concurrent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

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

    @Test
    public void oneByone() {

        /**
         * 自旋，死循环函数
         * 打印线程号
         */
        Consumer<String> FUNC_NEW_THREAD = t -> {
            for (; ; ) {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + ":" + t);
            }
        };

        Thread a = new Thread(() -> FUNC_NEW_THREAD.accept("A"), "线程1");
        Thread b = new Thread(() -> FUNC_NEW_THREAD.accept("B"), "线程2");
        Thread c = new Thread(() -> FUNC_NEW_THREAD.accept("C"), "线程3");

        /**
         * 怎么让线程A阻塞？
         */
        a.start();
        b.start();
        c.start();

        /**
         * 调度线程
         */
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                LockSupport.unpark(a);
            } else if (i % 3 == 1) {
                LockSupport.unpark(b);
            } else {
                LockSupport.unpark(c);
            }
            /**
             * 为什么要加sleep?
             * 如果不加，可能在A线程还没打印之前，B线程也被唤醒了，先打印了，这就顺序错乱了
             *
             * 或者怎么办，A线程执行完了，在唤醒B
             */
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    @Test
    public void oneByone2() {

        Thread a = new Thread(() -> {
            for (; ; ) {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + ":A");
            }
        }, "线程1");

        Thread b = new Thread(() -> {
            for (; ; ) {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + ":B");
            }
        }, "线程2");
        Thread c = new Thread(() -> {
            for (; ; ) {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + ":C");
                LockSupport.unpark(a);
            }
        }, "线程3");

        /**
         * 怎么让线程A阻塞？
         */
        a.start();
        b.start();
        c.start();

        /**
         * 调度线程
         */
        for (int i = 0; i < 30; i++) {
            LockSupport.unpark(a);
        }
    }
}
