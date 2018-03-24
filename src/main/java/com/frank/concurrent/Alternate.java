package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/23 0023 下午 4:49
 */
@Slf4j
public class Alternate implements Runnable {

    private Lock lock = new ReentrantLock();
    private int t = 1;
    private int j = 0;
    Condition first = lock.newCondition();
    Condition second = lock.newCondition();
    Condition third = lock.newCondition();

    public void one() {
        lock.lock();
        try {
            while (j != 0) {
                first.await();
            }
            log.info("first--------------");
            j = (j + 1) % 3;
            second.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void two() {
        lock.lock();
        try {
            while (j != 1) {
                second.await();
            }
            log.info("second--------------");
            j = (j + 1) % 3;
            third.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void three() {
        lock.lock();
        try {
            while (j != 2) {
                third.await();
            }
            log.info("third--------------");
            j = (j + 1) % 3;
            first.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (t == 1) {
                one();
                t = (t + 1) % 3;
            } else if (t == 2) {

                two();
                t = (t + 1) % 3;

            } else {
                three();
                t = (t + 1) % 3;
            }
        }
    }
}
