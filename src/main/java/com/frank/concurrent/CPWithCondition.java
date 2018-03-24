package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/24 0024 下午 3:05
 */

/**
 * condition
 * 干嘛用呢，比较精确地控制现成的唤醒，休眠。
 * 队列满了的时候，生产者等待，唤醒消费者；反之，。。。
 */
@Slf4j
public class CPWithCondition {
    private Lock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();
    final Integer[] pool = new Integer[10];
    // 缓冲池
    int count, cnum, pnum = 0;

    public void consume() {
        lock.lock();
        try {
            if (count == 0) {
                notEmpty.await();
            }

            int random = RandomUtils.nextInt(10, 100);
            // Thread.sleep(random);

            int value = pool[cnum];


            log.info("线程{},消费pool[{}],value = {}", Thread.currentThread().getId(), cnum, value);
            count--;
            notFull.signal();
            cnum++;
            if (cnum == 10) {
                cnum = 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void produce() {
        lock.lock();
        try {
            if (count == 10) {
                notFull.await();
            }
            int random = RandomUtils.nextInt(10, 50);
            // Thread.sleep(random);

            pool[pnum] = random;


            log.info("线程{},生产pool[{}],value = {}", Thread.currentThread().getId(), pnum, random);
            count++;
            notEmpty.signal();
            pnum++;
            if (pnum == 10) {
                pnum = 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static CPWithCondition condition = new CPWithCondition();

    public static void main(String[] args) {
        // 启动10个“写线程”，向BoundedBuffer中不断的写数据(写入0-9)；
        // 启动10个“读线程”，从BoundedBuffer中不断的读数据。

        for (int i = 0; i < 10; i++) {
            new PutThread("p" + i, i).start();
            new TakeThread("t" + i).start();
        }


    }

    static class PutThread extends Thread {
        private int num;

        public PutThread(String name, int num) {
            super(name);
            this.num = num;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10);    // 线程休眠1ms
                condition.produce();        // 向BoundedBuffer中写入数据
            } catch (InterruptedException e) {
            }
        }
    }

    static class TakeThread extends Thread {
        public TakeThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10);                    // 线程休眠1ms
                condition.consume();    // 从BoundedBuffer中取出数据
            } catch (InterruptedException e) {
            }
        }
    }
}
