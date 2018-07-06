package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinTask;
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
public class CountTask extends RecursiveTask<Integer> {

    /**
     * 英 [ˈθreʃhəʊld]
     * 美 [ˈθreʃhoʊld]
     * 阈值，临界值
     */
    private static final int THRESHOLD = 50;

    private int start = 0;
    private int end = 0;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        final boolean fork = needFork(start, end);

        if (fork) {
            log.info("need fork start={},end={}", start, end);
            /**
             * 分成两个子任务
             */
            int middle = (start + end) / 2;
            final CountTask oneTask = new CountTask(start, middle);
            final CountTask otherTask = new CountTask(middle + 1, end);

            final ForkJoinTask<Integer> fork1 = oneTask.fork();

            final ForkJoinTask<Integer> fork2 = otherTask.fork();


            final Integer join = oneTask.join();
            final Integer join1 = otherTask.join();
            sum = join + join1;
            log.info("need fork sum={}({}+{}),start={},end={}", sum, join, join1, start, end);

        } else {

//            if (start < 500) {
//                final int time = RandomUtils.nextInt(500, 1000);
//                try {
//                    Thread.sleep(time);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


            for (int i = start; i <= end; i++) {
                sum += i;
            }
            log.info("do not need fork sum={},start={},end={}", sum, start, end);
        }
        return sum;
    }

    /**
     * 是否需要fork
     *
     * @param start
     * @param end
     * @return
     */
    private boolean needFork(int start, int end) {
        return end - start > THRESHOLD ? true : false;
    }
}
