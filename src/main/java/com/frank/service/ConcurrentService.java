package com.frank.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/4 0004 下午 9:24
 */
@Slf4j
@Service
public class ConcurrentService {

//    @Autowired
//    @Qualifier(value = "testTaskPoolExecutor")
//    private Executor testTaskPoolExecutor;


    @Autowired
    @Qualifier(value = "completionService")
    private CompletionService completionService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    CompletionService<Integer> cService = new ExecutorCompletionService<Integer>(executorService);

    @Async("testTaskPoolExecutor")
    public Integer getNumber(){
        int i = RandomUtils.nextInt(50, 100);
        try {
            log.info("current thread is {},number is {}",Thread.currentThread().getId(),i);
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void justForTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    final int number = RandomUtils.nextInt(100, 200);
                    Thread.sleep(number);
                    log.info("ThreadId={},number={}", Thread.currentThread().getId(), number);
                    return number;
                }
            });
        }

        for (int i = 0; i < 10; i++) {

            try {
                Future<Integer> take = completionService.take();
                Integer integer = take.get();
                log.info("integer={}", integer);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
