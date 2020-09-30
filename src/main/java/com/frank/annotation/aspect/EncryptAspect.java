package com.frank.annotation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author frank
 * @version 1.0
 * @date 2020/9/6 0006 下午 4:20
 */
@Aspect
@Component
public class EncryptAspect {
    @Pointcut("@annotation(com.frank.annotation.Encrypt)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("-------EncryptAspect-----------");
    }


}
