package com.frank.proxy;

/**
 * Created by QuantGroup on 2018/6/15.
 */
public class MyProxyFactory {
    public static DynamicProxy2 getProxy2(){
        return new DynamicProxy2();
    }

    public static DynamicProxy getProxy(){
        return new DynamicProxy();
    }
}
