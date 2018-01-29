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
                    sleepMillis = RandomUtils.nextInt(0, 1000);
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
