package com.frank.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/28 0028 下午 2:33
 */
@Slf4j
public class TimeAfterAdvice implements AfterReturningAdvice {
    /**
     *
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Throwable
     */
    private ThreadLocal<Long> time = new ThreadLocal<>();
    @Override
    public void afterReturning(Object arg0, Method arg1, Object[] arg2,
                               Object arg3) throws Throwable {
        final Long before = time.get();
        log.info("[TimeAfterAdvice]before={}", before);
        log.info("[TimeAfterAdvice]time={}", LocalDateTime.now());
        log.info("[TimeAfterAdvice]method={}", arg1);
        log.info("[TimeAfterAdvice]args={}", arg2);
        log.info("[TimeAfterAdvice]object={}", arg3);
    }


}
