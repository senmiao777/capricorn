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

import java.math.BigDecimal;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 1:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = SemaphoreTest.class)
@Rollback(false)
@Slf4j
public class SemaphoreTest {

    @Autowired
    private ConcurrentService concurrentService;

    @Test
    public void t() {
        BigDecimal amount = new BigDecimal("77.8");
        Semaphore atmNumber = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            final Future<BigDecimal> deposit = concurrentService.deposit(i,amount, atmNumber);
            log.info("deposit result={}",deposit);
        }

    }


}
