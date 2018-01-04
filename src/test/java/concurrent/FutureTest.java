package concurrent;

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

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 1:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages ="com.frank")
@SpringBootTest(classes= FutureTest.class)
@Rollback(false)
@Slf4j
public class FutureTest {

    @Autowired
    private ConcurrentService concurrentService;
    /**
     * ThreadPoolTaskExecutor 线程池测试
     */
    @Test
    public void testS() {

        log.info("---  just for test begin");
        for (int i =0;i<50;i++){
            final Integer number = concurrentService.getNumber();
        }

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }


    /**
     * CompletionService 测试
     */
    @Test
    public void testS2() {

        log.info("---  just for test begin");
        try {
            for (int i = 0; i < 20; i++) {

                try{
                    Future<Integer> numberFuture = concurrentService.getNumberFuture();
                    log.info("numberFuture.isDone={}", numberFuture.isDone());
                    log.info("numberFuture={}", numberFuture.get(35, TimeUnit.MILLISECONDS));
                }catch (Exception e) {
                    log.error("Exception={}",e);
                }


            }
        } catch (Exception e) {
            log.error("testS2 Exception={}",e);
        }

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }
}
