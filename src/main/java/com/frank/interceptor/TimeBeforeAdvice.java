package com.frank.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/28 0028 下午 2:33
 */
@Slf4j
public class TimeBeforeAdvice implements MethodBeforeAdvice {
    /**
     * @param method  目标类的方法
     * @param objects 目标类方法参数
     * @param o       目标类的实例
     * @throws Throwable
     */
    private ThreadLocal<Long> time = new ThreadLocal<>();
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {

        time.set(System.currentTimeMillis());
        log.info("[TimeBeforeAdvice]time={}", LocalDateTime.now());
        log.info("[TimeBeforeAdvice]method={}", method);
        log.info("[TimeBeforeAdvice]args={}", objects);
        log.info("[TimeBeforeAdvice]object={}", o);
    }
}
