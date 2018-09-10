package com.frank.annotation.aspect;

import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class MDCLogAspect {
    @Pointcut("@annotation(com.frank.annotation.MDCLog)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MDC.put(Common.TRACING_ID.getValue(), String.valueOf(RandomUtils.nextInt(100000000, 999999999)));
        MDC.put(Common.REQUEST_AT.getValue(), String.valueOf(System.currentTimeMillis()));
        log.info("[MDCLogAspect]before {}", joinPoint.getSignature());
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        log.info("[MDCLogAspect]after {}", joinPoint.getSignature());
        log.info("方法{},耗时{}ms", joinPoint.getSignature().getName(), System.currentTimeMillis() - Long.valueOf(Objects.toString(MDC.get(Common.REQUEST_AT.getValue()), "0")));
        MDC.remove(Common.TRACING_ID.getValue());
    }
}
