package com.frank.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    /**
     * 多线程池
     * @return
     */
    @Bean(name = "testTaskPoolExecutor")
    public Executor testTaskPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        //最大运行1W队列
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("testpool-task-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // 如果QueueCapacity 都爆了 ,使用主线程跑任务  CallerRunsPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(name = "completionService")
    public CompletionService CompletionService() {
        return new ExecutorCompletionService(testTaskPoolExecutor());
    }

   // CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
}
