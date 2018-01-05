package concurrent;

import com.frank.service.ConcurrentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
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
     * 返回值为Future的多线程测试
     */
    @Test
    public void testS2() {

        log.info("---  just for test begin");
        List<Future<String>> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Future<String> future = concurrentService.testFuture(String.valueOf(RandomUtils.nextInt(1000,7000)));
            list.add(future);
        }

        log.info("$$$$$$$$$$$$$$$$$$$$$$$ list.size()={}",list.size());

        list.stream().forEach(future ->{
            try {
                String str = future.get(40, TimeUnit.MILLISECONDS);
                log.info("----------------str={}", str);
            } catch (Exception e) {
                final boolean cancel = future.cancel(true);
                log.error("cancel={}", cancel);
            }
        });



      /*  for (int i=0;i<list.size();i++){
            Future<String> future = list.get(i);
            try {
                String str = future.get(40, TimeUnit.MILLISECONDS);
                log.info("----------------str={}", str);
            } catch (Exception e) {
                final boolean cancel = future.cancel(true);
                log.error("cancel={},i={}", cancel,i);
            }
        }
*/
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }
}
