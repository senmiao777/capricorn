package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/29 0029 下午 9:16
 */
@Slf4j
public class Consumer implements Runnable {
    /**
     * LinkedBlockingQueue对于生产者端和消费者端分别采用了独立的锁来控制数据同步，
     * 这也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，
     * 以此来提高整个队列的并发性能。
     */
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        log.info("[Consumer] 启动消费者线程...");

        Random r = new Random();
        boolean isRunning = true;
        int sleepMillis;
        try {
            while (isRunning) {
                log.info("[Consumer]正从队列获取数据...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    sleepMillis = RandomUtils.nextInt(0, 100);
                    log.info("[Consumer]poll到数据 data={},正在消费...costTime={}", data, sleepMillis);
                    Thread.sleep(sleepMillis);
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            log.error("[Consumer]e={}", ExceptionUtils.getStackTrace(e));

        } finally {
            log.error("[Consumer]退出消费者线程");
        }
    }
}
