package concurrent;

import com.frank.concurrent.FinalTest;
import com.frank.service.ConcurrentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 1:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = ThreadLocalTest.class)
@Rollback(false)
@Slf4j
public class ThreadLocalTest {


    @Autowired
    private ConcurrentService concurrentService;

    /**
     * 返回值为Future的多线程测试
     */
    @Test
    public void testS2() {

        log.info("---  just for test begin");
        final FinalTest f1 = new FinalTest();
        Runnable runnable1 = () -> {
            f1.getNumber();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {

            }
            f1.setNumber(77);
            f1.getNumber();
        };

        Runnable runnable2 = () -> {
            FinalTest f2 = new FinalTest();
            f2.getNumber();
        };

        Runnable runnable3 = () -> {
            FinalTest f3 = new FinalTest();
            f3.getNumber();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
            f3.setNumber(17);
        };
        new Thread(runnable1).start();
        new Thread(runnable2).start();
        new Thread(runnable3).start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }
}
