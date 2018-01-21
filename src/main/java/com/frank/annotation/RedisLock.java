package com.frank.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/17 0017 下午 8:19
 * @description 用于防止重复关键操作（如提交），防止生成重复数据
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RedisLock {

    /**
     * 默认时间
     */
    int DEFAULT_MAX_WAIT_TIME = 7 * 1000;
    /**
     * key 前缀，默认取方法名
     * @return
     */
    String perfix() default "";

    /**
     * redis key
     * @return
     */
    String key();

    /**
     * 默认等待（阻塞）时间，超过该时间无响应抛异常
     * @return
     */
    int maxWaitTime() default DEFAULT_MAX_WAIT_TIME;

    int maxExpireTime() default DEFAULT_MAX_WAIT_TIME;
}
