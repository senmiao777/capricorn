package com.frank.proxy;

import org.springframework.aop.framework.AopProxy;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/28 0028 下午 2:
 * <p>
 * org.springframework.aop.framework.ProxyFactory，这个内部就是使用了我们之前的JDK代理或者CGLib代理的技术，将增强应用到目标类中。
 * Spring定义了org.springframework.aop.framework.AopProxy接口，并提供了两个final的实现类，其中：
 * <p>
 * Cglib2AopProxy使用CGLib代理技术创建代理，而JdkDynamicAopProxy使用JDK代理技术创建代理；
 * <p>
 * 如果通过ProxyFactory的setInterfaces(Class[] interfaces)指定针对接口进行代理，ProxyFactory就使用JdkDynamicAopProxy；
 * <p>
 * 如果是通过类的代理则使用Cglib2AopProxy，另外也可以通过ProxyFactory的setOptimize(true)方法，让ProxyFactory启动优化代理模式，这样针对接口的代理也会使用Cglib2AopProxy。
 */
public class SringProxy {
    AopProxy proxy;
}
