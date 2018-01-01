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
    public void testS(){
        log.info("---  just for test begin ");
        SyncTest obj = new SyncTest();
        String name = "T1";
        String anotherName = "T2";

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.staticMethod1();
            }
        },name).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.staticMethod2();
            }
        },anotherName).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.normalMethod1();
            }
        },anotherName).start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }

    @Test
    public void test(){
        log.info("---  just for test begin ");
        SyncTest obj = new SyncTest();
//        new Thread(new FutureTask<Integer>(new SyncTest())).start();
//        new Thread(new FutureTask<Integer>(new SyncTest())).start();
        String name = "T1";
        String anotherName = "T2";

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.syncMethod(name);
            }
        },name).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.syncMethod(anotherName);
            }
        },anotherName).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.syncMethod(name);
            }
        },name).start();

        new Vector<String>();



        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");
    }
}
