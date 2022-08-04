package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 12:45
 */
@Slf4j
public class SyncTest<Integer> implements Callable<Integer> {
    private final Object lock = new Object();
    private int count = 0;

    private static volatile boolean init = false;

    // @Override
    public void run() {
        log.info("ThreadId={}", Thread.currentThread().getId());
    }

    @Override
    public Integer call() throws Exception {
        //final int i = RandomUtils.nextInt(100, 1000);
        //syncMethod();
        initMethod();
        return null;
    }

    public void initMethod() {
        System.out.println("initMethod 111111");
        try {
            Thread.sleep(20);
        } catch (Exception e) {
            System.out.println("error");
        }
        synchronized (SyncTest.class) {
            if (init) {
                System.out.println("do nothing -----------");
                return;
            }
            init = true;
        }
        System.out.println("业务代码执行");
    }


    public synchronized int syncMethod() {
        final int s = RandomUtils.nextInt(500, 1000);
        log.info("syncMethod ThreadId={},time={},sleepMillis={}", Thread.currentThread().getId(), LocalDateTime.now(), s);
        try {
            int i = 5;
            while (i-- > 0) {
                log.info("ThreadId ={},i={}", Thread.currentThread().getId(), i);
                try {
                    Thread.sleep(RandomUtils.nextInt(10, 100));
                } catch (InterruptedException ie) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * synchronized 修饰方法，多个线程访问 同一个对象的 同步 方法时，只有一个线程能获得锁。锁住的是对象，等同于synchronized（this）
     * synchronized 代码块，注意是对谁加锁，加的不是同一把锁，根本不起作用。
     *
     * @param lock
     * @return
     */
    public int syncMethod(String lock) {
        // synchronized (this.lock) {
        synchronized (lock) {
            final int n = RandomUtils.nextInt(500, 1000);
            log.info("syncInnerMethod ThreadId={},name={},time={},sleepMillis={}", Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now(), n);
            show();
            return n;
        }

    }

    /**
     * static synchronized 锁住的是所有static synchronized修饰的方法 + synchronized (Clazz.class) 范围的代码块
     * 调用任何一个static synchronized 修饰的方法，其他static synchronized修饰的方法都阻塞。
     * 同一时间，只能有一个线程可以调用
     * 比如 A、B、C 三个方法都是被static synchronized 修饰的方法，一个线程一下会锁住A、B、C三个方法，和对象无关。
     * synchronized代码块，用类.class加锁，效果和static synchronized一样
     * 没有static修饰的synchronized不影响。
     *
     * @return
     */
    public static synchronized int staticMethod1() {

        final int n = RandomUtils.nextInt(500, 1000);
        log.info("staticMethod1 ThreadId={},name={},time={},sleepMillis={}", Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now(), n);
        show();
        return n;


    }

    public synchronized int normalMethod1() {

        final int n = RandomUtils.nextInt(500, 1000);
        log.info("normalMethod1 ThreadId={},name={},time={},sleepMillis={}", Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now(), n);
        show();
        return n;


    }

    public synchronized int normalMethod2() {

        final int n = RandomUtils.nextInt(500, 1000);
        log.info("normalMethod2 ThreadId={},name={},time={},sleepMillis={}", Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now(), n);
        show();
        return n;


    }


    public static synchronized int staticMethod2() {

        final int n = RandomUtils.nextInt(500, 1000);
        log.info("staticMethod2 ThreadId={},name={},time={},sleepMillis={}", Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now(), n);
        show();
        return n;


    }


    /**
     * 同步代码块，锁为 clazz
     *
     * @return
     */
    public int innerSyncWithClass() {

        synchronized (SyncTest.class) {
            final int n = RandomUtils.nextInt(500, 1000);
            log.info("syncInnerMethod ThreadId={},name={},time={},sleepMillis={}", Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now(), n);
            show();
            return n;
        }


    }

    /**
     * 打印日志，方便观察线程执行情况
     *
     * @return
     */
    private static void show() {
        int i = 5;
        while (i-- > 0) {
            log.info("ThreadId ={},i={}", Thread.currentThread().getId(), i);
            try {
                Thread.sleep(RandomUtils.nextInt(10, 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
