package concurrent;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2021/5/22 0022 上午 10:48
 */
public class CommonTest {


    /**
     * newScheduledThreadPool
     * 指定延迟多久后，定时执行
     *
     * @throws InterruptedException
     */
    @Test
    public void testExecuotrService() throws InterruptedException {
        System.out.println("当前时间:" + LocalDateTime.now().getSecond());
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            System.out.println("间隔执行:" + LocalDateTime.now().getSecond());
        }, 2, 1, TimeUnit.SECONDS);
        Thread.sleep(5000L);

        System.out.println("当前时间2:" + LocalDateTime.now().getSecond());
    }

}
