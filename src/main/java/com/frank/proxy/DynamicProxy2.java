package com.frank.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author frank
 * @version 1.0
 * @date 2018/6/15
 */
@Slf4j
public class DynamicProxy2 implements InvocationHandler {

    Object originObject;

    public Object proxy(Object currentObject) {
        this.originObject = currentObject;
        return Proxy.newProxyInstance(originObject.getClass().getClassLoader(), originObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(method, args);
        final Object invoke = method.invoke(originObject, args);
        after(method, args);
        return invoke;
    }

    public void before(Method method, Object[] args) {
        log.info(" DynamicProxy2 before calling method={},args={}", method, args);
    }

    public void after(Method method, Object[] args) {
        log.info(" DynamicProxy2 after calling method={},args={}", method, args);
    }
}
