package com.frank.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/4 0004 下午 9:24
 */
@Slf4j
@Service
public class ConcurrentService {


    @Autowired
    @Qualifier(value = "completionService")
    private CompletionService completionService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 循环次数
     */
    private static final int TOTAL = 4;

    /**
     * 默认返回值
     */
    private static final int DEFAULT_RETURN_NUMBER = -1;

    /**
     * 默认返回值
     */
    private static final String DEFAULT_RETURN_STRING = "default";

    /**
     * 默认返回值
     */
    private static final BigDecimal DEFAULT_RETURN_AMOUNT = BigDecimal.ZERO;

    @Async("testTaskPoolExecutor")
    public Integer getNumber() {
        int i = RandomUtils.nextInt(50, 100);
        try {
            log.info("--getNumber in-- current thread is {},number is {}", Thread.currentThread().getId(), i);
            Thread.sleep(i);
            log.info("--getNumber after sleep-- current thread is {},number is {}", Thread.currentThread().getId(), i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @param mark 无实际意义，用于观察当前任务
     * @return Integer sleep毫秒数
     */
    @Async("testTaskPoolExecutor")
    public Future<String> testFuture(String mark) {
        try {
            int i = RandomUtils.nextInt(5, 50);
            for (int k = 1; k < TOTAL; k++) {
                log.info("----------in---------ThreadId={},cycle time={},sleep={},t={}", Thread.currentThread().getId(), k, i, mark);
                Thread.sleep(i);
                log.info("-------after sleep---ThreadId={},cycle time={},sleep={},t={}", Thread.currentThread().getId(), k, i, mark);
            }
            return new AsyncResult<>(mark);
        } catch (Exception e) {
            return new AsyncResult<>(DEFAULT_RETURN_STRING);
        }
    }


    /**
     * 存款操作
     * @param amount 存钱数量
     * @param atm 自动取款机 数量
     */
    @Async("testTaskPoolExecutor")
    public Future<BigDecimal> deposit(int i,BigDecimal amount,Semaphore atm) {
        try {
            int sleepMillis = RandomUtils.nextInt(5, 50);
           //atm.
            atm.acquire();
                log.info("[ConcurrentService][deposit]第 {} 个用户正在存钱,amount={},atm={},sleepMillis={}", amount, atm, sleepMillis);
                Thread.sleep(sleepMillis);
            return new AsyncResult<>(amount);
        } catch (Exception e) {
            return new AsyncResult<>(DEFAULT_RETURN_AMOUNT);
        }finally {
            atm.release();
        }
    }

}
