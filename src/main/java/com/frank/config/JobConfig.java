package com.frank.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.frank.job.simple.MyFirstJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author frank
 * @version 1.0
 * @date 2020/6/19 0019 下午 10:44
 */
@Configuration
public class JobConfig {
    @Autowired
    private ZookeeperRegistryCenter regCenter;

    @Bean
    public SimpleJob myFirstJob() {
        return new MyFirstJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler myFirstJobScheduler(final SimpleJob myFirstJob, @Value("${myFirstJob.cron}") final String cron,
                                            @Value("${myFirstJob.shardingTotalCount}") final int shardingTotalCount,
                                            @Value("${myFirstJob.shardingItemParameters}") final String shardingItemParameters) {
        LiteJobConfiguration jobConfiguration = createJobConfiguration(myFirstJob.getClass(), cron, shardingTotalCount, shardingItemParameters);
        return new JobScheduler(regCenter, jobConfiguration);
    }

    @SuppressWarnings("rawtypes")
    private LiteJobConfiguration createJobConfiguration(Class<? extends SimpleJob> jobClass, String cron, int shardingTotalCount
            , String shardingItemParameters) {
        // 定义作业核心配置
//        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
//        // 定义Dataflow类型配置
//        // false 非流式处理数据: 只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业
//        //DataflowJobConfiguration jobConfiguration = new DataflowJobConfiguration(coreConfig, jobClass.getCanonicalName(), false);
//        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
//        // 定义Lite作业根配置
//        LiteJobConfiguration rootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
//        return rootConfig;

        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).
                shardingItemParameters(shardingItemParameters).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, jobClass.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return simpleJobRootConfig;
    }
}
