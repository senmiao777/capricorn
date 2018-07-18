package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.concurrent.RecursiveTask;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/6
 * <p>
 * <p>
 * ForkJoinTask：我们要使用ForkJoin框架，必须首先创建一个ForkJoin任务。
 * 它提供在任务中执行fork()和join()操作的机制，通常情况下我们不需要直接继承ForkJoinTask类，而只需要继承它的子类
 * Fork/Join框架提供了以下两个子类：
 * RecursiveAction：用于没有返回结果的任务。
 * RecursiveTask ：用于有返回结果的任务。
 * ForkJoinPool ：ForkJoinTask需要通过ForkJoinPool来执行，任务分割出的子任务会添加到当前工作线程所维护的双端队列中，
 * 进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。
 * <p>
 * ForkJoinTask和一般的Task主要区别是ForkJoinTask需要实现compute方法
 * 当任务数量小于阈值的时候，直接执行；当任务数量大于阈值的时候，分成两个子任务。
 * 每个子任务在调用fork方法的时候，又会进入compute方法。
 * 。。。
 * 如此递归去，直到任务小于阈值，直接执行。
 */
@Slf4j
public class CountFileTask extends RecursiveTask<Integer> {

    private File file;

    public CountFileTask(File f) {
        this.file = f;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        File[] list = file.listFiles();

        for (File f : list) {
            if (f.isDirectory()) {
                CountFileTask cft = new CountFileTask(f);
                cft.fork();
                sum += cft.join();
            } else {
                sum++;
            }
        }
        return sum;

    }
}
