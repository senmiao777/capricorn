package com.frank.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2020/6/21 0019 下午 10:44
 */
@Slf4j
public class MySecondJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("MySecondJob shardingContext={}", shardingContext);
    }
}
