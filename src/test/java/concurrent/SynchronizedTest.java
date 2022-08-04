package concurrent;

import com.frank.concurrent.Alternate;
import com.frank.concurrent.SyncTest;
import com.frank.entity.mysql.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Vector;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 1:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication
//@ComponentScan(basePackages ="com.frank")
//@SpringBootTest(classes= StockTest.class)
@Rollback(false)
@Slf4j
public class SynchronizedTest {



    @Test
    public void abcd() throws InterruptedException {
        testHeap();
        Sync2Test syncTest = new Sync2Test();
        Sync2Test syncTest2 = new Sync2Test();
        Thread thread = new Thread(() -> syncTest.incr(),"t1");
        Thread threadTwo = new Thread(() -> syncTest2.incr(),"t2");
        thread.start();
        threadTwo.start();
//        thread.join();
//        threadTwo.join();
        Thread.sleep(1000);
        //最终打印结果是20000，如果不使用synchronized修饰，就会导致线程安全问题，输出不确定结果
        System.out.println("-----------------"+syncTest.getJ());
        System.out.println("-----------------"+syncTest2.getJ());
    }

    private void testHeap(){
        User user = User.generateUser();
        System.out.println("user="+user);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void abc(){
        Alternate alternate = new Alternate();
        Thread one = new Thread(alternate);
        Thread two = new Thread(alternate);
        Thread three = new Thread(alternate);
        one.start();
        two.start();
        three.start();
    }

    /**
     * static synchronized 修饰方法测试
     */
    @Test
    public void testS() {
        log.info("---  just for test begin ");
        SyncTest obj = new SyncTest();
        SyncTest obj2 = new SyncTest();
        SyncTest obj3 = new SyncTest();
        String name = "T1";
        String anotherName = "T2";

        // static synchronized 修饰方法
        new Thread(()-> {
                obj.initMethod();
        }, "obj - staticMethod1 ").start();

        //static synchronized 修饰方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                obj2.initMethod();
            }
        }, "obj2 - staticMethod2 ").start();

       /* // 普通synchronized方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.normalMethod1();
            }
        }, "normal synchronized").start();
*/
        // synchronized 代码块
        new Thread(new Runnable() {
            @Override
            public void run() {
                obj3.initMethod();
            }
        }, "synchronized block").start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }

    /**
     * synchronized 锁住的是对象
     */
    @Test
    public void test() {
        log.info("---  just for test begin ");
        SyncTest obj = new SyncTest();
        SyncTest obj2 = new SyncTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.normalMethod1();
            }
        }, "obj - normalMethod1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.normalMethod2();
            }
        }, "obj - normalMethod2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj2.normalMethod2();
            }
        }, "obj2 - normalMethod2").start();

        new Vector<String>();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");
    }

    /**
     * 两个线程交替执行
     * wait
     * notifyAll
     * <p>
     * name
     */
    @Test
    public void testAlternative() {
        final Object lock = new Object();
        final int status = 0;
        Thread job1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    synchronized (lock) {

                        log.info("----------1-----------");
                        lock.notifyAll();
                        try {
                            log.info("job1 第{}次执行", i + 1);
                            if (status == 1) {
                                lock.wait();
                            }


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
        Thread job2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    synchronized (lock) {

                        log.info("``````````````````2111111111111");
                        lock.notifyAll();
                        try {
                            log.info("job2 第{}次执行", i + 1);
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        job1.start();
        job2.start();

    }

    public static int status = 3;

    @Test
    public void testOutput() {
        final Object lock = new Object();


        Thread odd = new Thread(new Runnable() {
            int num = 1;
            // 计数
            int count = 1;

            @Override
            public void run() {

                while (num < 100) {
                    synchronized (lock) {
                        /**
                         * 这种写法很容易死锁
                         * 拿到锁之后判断不符合条件，直接等待；而符合条件的已经出去等待状态->死锁
                         * 现在这样依旧会死锁， 3 在等待， 但是锁一直在1 和 2 之间
                         */
                        if (status == 1) {
                            log.info("第{}个奇数是{}", count, num);
                            num = num + 2;
                            count++;
                            // lock.notifyAll();
                            status = 2;
                        } else {
                            lock.notifyAll();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            }
        });

        Thread even = new Thread(new Runnable() {
            int num = 2;
            // 计数
            int count = 1;

            @Override
            public void run() {

                while (num < 100) {
                    synchronized (lock) {
                        if (status == 2) {
                            log.info("第{}个偶数是{}", count, num);
                            num = num + 2;
                            count++;
                            status = 3;
                        } else {
                            lock.notifyAll();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            }
        });

        Thread other = new Thread(new Runnable() {
            int num = 2;
            // 计数
            int count = 1;

            @Override
            public void run() {

                while (num < 100) {
                    synchronized (lock) {
                        if (status == 3) {
                            log.info("第{}个other是{}", count, RandomUtils.nextInt(100, 200));
                            num = num + 2;
                            status = 1;
                            count++;
                        } else {
                            lock.notifyAll();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            }
        });

        odd.start();
        even.start();
        other.start();
    }
}
