package com.frank.concurrent;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 上午 9:21

@Slf4j
public class CallableAndFuture {
    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                final int number = RandomUtils.nextInt(100, 1000);
                Thread.sleep(number);// 可能做一些事情
                log.info("Threadid={},number={}",Thread.currentThread().getId(),number);
                return number;
            }
        };




        FutureTask<Integer> future = new FutureTask<Integer>(callable);
      //  future.wait();
        new Thread(future).run();
        try {
            log.info("1 future.isDone() ={},ThreadId={}", future.isDone(),Thread.currentThread().getId());
            Thread.sleep(RandomUtils.nextInt(100, 1000));// 可能做一些事情
            log.info("2 future.isDone() ={}", future.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 */