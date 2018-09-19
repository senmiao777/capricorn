package com.frank.designpattern.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/28 0028 上午 11:39
 * <p>
 * spring的动态代理只能为接口创建代理对象，那想为没有实现接口的类创建代理怎么办？
 * ->cglib动态代理
 * CGLib所创建的动态代理对象的性能比JDK的高大概10倍，但CGLib在创建代理对象的时间比JDK大概多8倍，
 * 所以对于singleton的代理对象或者具有实例池的代理，因为无需重复的创建代理对象，所以比较适合CGLib动态代理技术，反之选择JDK代理。
 * 值得一提的是由于CGLib采用动态创建子类的方式生成代理对象，所以不能对目标类中final的方法进行代理
 */
@Slf4j
public class CglibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public Object proxy(Class clazz) {
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    @Override
    public Object intercept(Object arg0, Method arg1, Object[] arg2,
                            MethodProxy arg3) throws Throwable {

        log.info("[CglibProxy][intercept]getName()={},arg1.getName() you can do sth here,like @Before", arg0.getClass().getName(), arg1.getName());
        Object result = arg3.invokeSuper(arg0, arg2);
        log.info("[CglibProxy][intercept]getName()={},arg1.getName() you can do sth here,like @After", arg0.getClass().getName(), arg1.getName());
        return result;
    }
}
