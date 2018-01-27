package com.frank.annotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 4:21
 * 代理原理示意
 */
// 抽象角色：声明真实对象和代理对象的共同接口
@Slf4j
abstract public class Subject {
    abstract public void request();
}

// 真实角色：实现了Subject的request()方法
@Slf4j
class RealSubject extends Subject {
    public RealSubject() {
    }

    @Override
    public void request() {
        log.info(" From real subject. ");
    }
}

// 代理角色：
@Slf4j
class ProxySubject extends Subject {
    /**
     * 以真实角色作为代理角色的属性
     */
    private Subject realSubject;

    public ProxySubject(Subject realSubject) {
        this.realSubject = realSubject;
    }

    // 该方法封装了真实对象的request方法
    @Override
    public void request() {
        preRequest();
        // 此处执行真实对象的request方法
        realSubject.request();
        postRequest();
    }

    private void preRequest() {
        log.info("preRequest");
    }

    private void postRequest() {
        log.info("postRequest");
    }

}


    /*// 客户端调用：
    RealSubject real = new RealSubject();
    Subject sub = new ProxySubject(real);
Sub.request();*/
