package com.frank.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ExecutorConfig {

    /**
     * 多线程池
     * ThreadPoolTaskExecutor 和 ThreadPoolExecutor什么区别？
     * ThreadPoolTaskExecutor是sping实现的线程池，比ThreadPoolExecutor功能多一点（比如定时调度功能），类似一个装饰器
     *
     * @return
     */
    @Bean(name = "testTaskPoolExecutor")
    public Executor testTaskPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        //最大运行1W队列
        executor.setQueueCapacity(1000);
        /**
         * 最大空闲时间
         * 超过这个时间，大于maxCorePoolSize的空闲线程将被终止
         */
        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("testpool-task-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // 如果QueueCapacity 都爆了 ,使用主线程跑任务  CallerRunsPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }


    @Bean(name = "mailPoolExecutor")
    public Executor mailPoolExecutor() {
        final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1L, TimeUnit.SECONDS, queue);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                /**
                 * 当线程池满了的时候，自定义处理方式
                 */
            }
        });
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean(name = "completionService")
    public CompletionService CompletionService() {
        return new ExecutorCompletionService(testTaskPoolExecutor());
    }

    // CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
}
