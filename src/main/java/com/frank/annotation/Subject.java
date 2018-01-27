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
    /**
     * 仅供示意
     */
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

/**
 * 代理角色：
 * 代理对象内部包含有真实角色的引用，从而可以操作真实角色.
 * 代理对象与真实对象有相同的接口，能在任何时候代替真实对象
 * 代理对象可以在执行真实对象前后加入特定的逻辑以实现功能的扩展。
 *
 *  客户端调用：
 *  RealSubject real = new RealSubject();
 *  Subject sub = new ProxySubject(real);
 *  sub.request();
 *
 */
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



