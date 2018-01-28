package com.frank.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 4:21
 * 代理原理示意
 * 该类为静态代理代码示意
 * <p>
 * 代理角色：
 * 代理对象内部包含有真实角色的引用，从而可以操作真实角色.
 * 代理对象与真实对象有相同的接口，能在任何时候代替真实对象
 * 代理对象可以在执行真实对象前后加入特定的逻辑以实现功能的扩展。
 * <p>
 * 客户端调用：
 * RealSubject real = new RealSubject();
 * Subject sub = new ProxySubject(real);
 * sub.request();
 * 由以上代码可以看出，客户实际需要调用的是RealSubject类的request()方法，现在用 ProxySubject来代理 RealSubject类，
 * 同样达到目的，同时还封装了其他方法 (preRequest(),postRequest())，可以处理一些其他问题。
 * <p>
 * 另外，如果要按照上述的方法使用代理模式，那么真实角色必须是事先已经存在的，并将其作为代理对象的内部属性。
 * 但是实际使用时，如果某一个代理要应用于一批真实角色，毎个真实对象必须对应一个代理角色，如果大量使用会导致类的急剧膨胀；
 * 此外，如果事先并不知道真实角色，该如何使用编写代理类呢？
 * 这个问题可以通过java的动态代理类来解决。
 * <p>
 * 动态代理能解决什么问题呢?
 * 1、事先不知道真实对象是什么，我不知道要对什么方法进行增强。
 * 2、就算我知道我事先要增强一千个方法，我要写一千个代理类，不累死？
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



