package com.frank.config;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerAdapter;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author frank
 * @version 1.0
 * @date 2017/12/10 0010 下午 2:00
 */
@Slf4j
@Configuration
public class MyStatLog extends DruidDataSourceStatLoggerAdapter implements DruidDataSourceStatLogger {
    @Override
    public void log(DruidDataSourceStatValue statValue) {
        log.info("MyStatLog in ...");
        super.log(statValue);
        log.info("MyStatLog out ... statValue={}",statValue.toString());
    }
}
