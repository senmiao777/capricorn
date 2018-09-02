package com.frank.annotation.aspect;

import com.frank.annotation.RedisLock;
import com.frank.exception.ResubmitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/17 0017 下午 8:19
 *
 * 通知 = 在什么时候（Before，After。。。） + 干什么事（Before里的具体业务逻辑）
 * 连接点 ：所有可以应用通知的点比如我有十个方法，我就可以说，有十个连接点
 * 切点 ： 应用了通知的点
 * 切面 = 通知 + 切点 ，即在哪个方法上，方法的之前还是之后，分别干了什么事
 * 织入 ： 在运行时将切面应用到目标方法，并创建代理对象的过程。
 * 在调用请求到目标方法之前，会被代理对象拦截，代理对象会先执行切面的逻辑，然后再把调用请求转发给真正的目标方法
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

    private final String PREFIX_SPEL = "#this";
    private final String COLON = ":";
    private final String PREFIX_LOCK = "RedisLock:";
    private final Long ONE = 1L;
    private final Long ZERO = 0L;
    private ThreadLocal<String> lockName = new ThreadLocal<>();

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Pointcut无实际意义，只是为了方便引用，不用before写一次@annotation(com.frank.annotation.RedisLock)
     * after再写一次@annotation(com.frank.annotation.RedisLock)
     * afterthrowing再写一次。。。
     */
    @Pointcut("@annotation(com.frank.annotation.RedisLock)")
    public void annotationPointCut() {
    }


    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {

        log.info("[RedisLockAspect] ---before---");
        String lockKey = getLockKey(joinPoint);
        /**
         * 默认值 1
         * redis 报错，increment =1 ,程序可以继续走
         * 高并发重复提交是极少数情况，不应该因为redis的问题儿影响主流程执行
         */
        Long increment = ONE;
        try {
            increment = redisTemplate.opsForValue().increment(lockKey, ONE);
        } catch (Exception e) {
            log.error("[RedisLockAspect] redis Exception e={}", ExceptionUtils.getStackTrace(e));
        }
        /**
         * 当前lockKey未被占用，可以继续执行
         */
        if (ONE.equals(increment)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request != null) {
                log.info("URL :{} ", request.getRequestURL().toString());
                log.info("HTTP_METHOD : {}", request.getMethod());
                log.info("IP :{} ", request.getRemoteAddr());
                log.info("PACKAGE :{}", joinPoint.getSignature().getDeclaringTypeName());
                log.info("CLASS_METHOD :{}", joinPoint.getSignature().getName());
                log.info("ARGS :{} ", Arrays.toString(joinPoint.getArgs()));
                //获取所有参数方法一：
                Enumeration<String> enu = request.getParameterNames();

                while (enu.hasMoreElements()) {

                    String paraName = (String) enu.nextElement();
                    log.info("paraName={}", request.getParameter(paraName));

                }
            } else {
                log.info("request is null={}");
            }
        } else {
            throw new ResubmitException("当前lockKey已被占用");
        }


    }

    /**
     * After约等于AfterReturning + AfterThrowing
     * AfterReturning 当正常返回时触发，异常时不触发
     * AfterThrowing 和 AfterReturning相反
     * After是正常返回或者抛异常后都会触发
     * 如果After和AfterReturning/AfterThrowing都定义了的话，先执行After的
     * 如果我就是想在目标方法执行后释放资源，不管你成不成功我都需要释放，那显然用After合适
     */
    @After("annotationPointCut()")
    public void after() {
        log.info("[RedisLockAspect] ---after---");
        final String lockKey = lockName.get();
        log.info("[RedisLockAspect] ---after---lockKey={}", lockKey);
        try {
            final Boolean expire = redisTemplate.expire(lockKey, ZERO, TimeUnit.MILLISECONDS);
            log.info("[RedisLockAspect] ---after---lockKey={},expire={}", lockKey, expire);
        } catch (Exception e) {
            log.error("[RedisLockAspect] after expire Exception.lockKey={}e={}", lockKey, ExceptionUtils.getStackTrace(e));
        }
    }

    @AfterReturning("annotationPointCut()")
    public void afterReturning() {
        log.info("[RedisLockAspect] ---afterReturning---");
    }

    @AfterThrowing("annotationPointCut()")
    public void afterThrowing() {
        log.info("[RedisLockAspect] ---afterThrowing---");
    }

    /**
     * 获取最终的redis key
     *
     * @param joinPoint
     * @return
     */
    private String getLockKey(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        final String name = method.getName();
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        String prefix = annotation.perfix();
        /**
         * 前缀默认取方法名
         */
        if (StringUtils.isBlank(prefix)) {
            prefix = name;
        }
        StringBuilder lockKey = new StringBuilder(PREFIX_LOCK).append(prefix).append(COLON);
        String key = annotation.key();
        if (key.startsWith(PREFIX_SPEL)) {
            try {
                Expression expression = new SpelExpressionParser().parseExpression(key);
                String value = expression.getValue(joinPoint.getArgs(), String.class);
                lockKey = lockKey.append(value);
            } catch (Exception e) {
                log.error("[RedisLockAspect]spel Exception.input spel key={},e={}", key, ExceptionUtils.getStackTrace(e));
                throw e;
            }
        } else {
            lockKey = lockKey.append(key);
        }
        /**
         * lockKey 存到ThreadLocal里，方便在After方法中获取，设置到期（删除lockKey值）
         */
        lockName.set(lockKey.toString());
        return lockKey.toString();
    }


/**
 * 获取注解类型
 *
 * @param joinPoint
 * @param clazz
 * @param <T>
 * @return public <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz) {
 * MethodSignature sign = (MethodSignature) joinPoint.getSignature();
 * <p>
 * Method method = sign.getMethod();
 * <p>
 * return method.getAnnotation(clazz);
 * }
 */
}
