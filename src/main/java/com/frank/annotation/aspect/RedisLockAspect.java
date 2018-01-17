package com.frank.annotation.aspect;

import com.frank.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/17 0017 下午 8:19
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

    @Pointcut("@annotation(com.frank.annotation.RedisLock)")
    public void annotationPointCut() {}


    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint){

        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        final String name1 = sign.getName();
        Method method = sign.getMethod();
        final String name = method.getName();
        RedisLock annotation = method.getAnnotation(RedisLock.class);

        //final RedisLock annotation = getAnnotation(joinPoint, RedisLock.class);

        final String perfix = annotation.perfix();
log.info("name={},perfix={},name1={}",name,perfix,name1);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null){
            System.out.println("URL : " + request.getRequestURL().toString());

            System.out.println("HTTP_METHOD : " + request.getMethod());

            System.out.println("IP : " + request.getRemoteAddr());

            System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

            System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

            //获取所有参数方法一：

            Enumeration<String> enu=request.getParameterNames();

            while(enu.hasMoreElements()){

                String paraName=(String)enu.nextElement();
                log.info("paraName={}",request.getParameter(paraName));

            }
        }else {
            log.info("request is null={}");
        }
    }

    /**
     * 获取注解类型
     * @param joinPoint
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz) {
        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();

        Method method = sign.getMethod();

        return method.getAnnotation(clazz);
    }
}
