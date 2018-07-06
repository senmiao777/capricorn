package com.frank.proxy;

/**
 * Created by frank on 2018/6/15.
 */
public class MyProxyFactory {
    public static DynamicProxy2 createProxy2() {
        return new DynamicProxy2();
    }

    public static DynamicProxy createProxy() {
        return new DynamicProxy();
    }

    public static CglibProxy cresteCglibProxy() {
        return new CglibProxy();
    }
}
