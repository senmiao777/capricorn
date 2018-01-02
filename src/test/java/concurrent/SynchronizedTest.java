package concurrent;

import com.frank.concurrent.SyncTest;
import lombok.extern.slf4j.Slf4j;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.staticMethod1();
            }
        }, "obj - staticMethod1 ").start();

        //static synchronized 修饰方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                obj2.staticMethod2();
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
                obj3.innerSyncWithClass();
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
}
