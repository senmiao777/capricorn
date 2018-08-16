package com.frank.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2018/8/15 0015 下午 10:17
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        final GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        /**
         * 最多缓存500 条，失效时间30分钟
         * GuavaCacheManager 的数据结构类似  Map<String,Map<Object,Object>>  map =new HashMap<>();
         */
        // guavaCacheManager.setCacheSpecification("maximumSize=500,expireAfterWrite=30m");
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

        // 多少时间没有被读/写则清除缓存
        cacheBuilder.expireAfterAccess(1L, TimeUnit.MINUTES);

        // 多长时间后回收
        cacheBuilder.expireAfterWrite(10L, TimeUnit.MINUTES);

        // Segment数量，数越大并发越强
        cacheBuilder.concurrencyLevel(7);

        // 缓存容量，超过容量后，按照LRU（最近最少使用算法，使用时间距离现在最久的被回收）进行缓存回收
        cacheBuilder.maximumSize(3000L);

        guavaCacheManager.setCacheBuilder(cacheBuilder);
        return guavaCacheManager;
    }
}
