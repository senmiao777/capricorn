package com.frank.proxy;
// 代理处理器：

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 4:58
 * 代理处理器
 * 所谓Dynamic Proxy是这样一种class：它是在运行时生成的class，在生成它时你必须提供一组interface给它，然后该class就宣称它实现了这些interface。
 * 你当然可以把该class的实例当作这些interface中的任何一个来用。
 * 当然啦，这个DynamicProxy其实就是一个Proxy， 它不会替你作实质性的工作，在生成它的实例时你必须提供一个handler，由它接管实际的工作。
 * <p>
 * <p>
 * 代理模式和装饰器模式的区别：
 * 装饰器模式：在不改变接口的前提下，动态扩展对象的功能，在不使用创造更多子类的情况下，将对象的功能加以扩展。
 * 代理模式：在不改变接口的前提下，控制对象的访问。
 * 你在一个地方写装饰，大家就知道这是在增加功能；你写代理，大家就知道是在限制，
 * <p>
 * 孙悟空扮演高家三小姐，所以可以说孙悟空与高家三小姐具有共同的接口。
 * 如果猪八戒只想见见高家三小姐的娇好面容，或者谈谈天说说地，那么高家三小姐的“代理”孙悟空是允许的。
 * 但猪八戒想XXOO，那么是不行的。这是保护代理模式的应用。只有代理对象认为合适时，才会将客户端的请求传递给真实主题对象。
 * <p>
 * <p>
 * 孙悟空有七十二般变化，在二郎神眼里，他永远是那只猢狲。
 * 装饰模式以对客户透明的方式动态地给一个对象附加上更多的责任。
 * 换言之，客户端并不会觉得对象在装饰前和装饰后有什么不同。
 * 装饰模式可以在不使用创造更多子类的情况下，将对象的功能加以扩展。
 * 他的每一种变化都给他带来一种附加的本领。他变成鱼儿时，就可以到水里游泳；他变成雀儿时，就可以在天上飞行。
 * 而不管悟空怎么变化，在二郎神眼里，他永远是那只猢狲。
 * <p>
 * AopProxy代理对象是在Bean初始化时生成的
 * 其中AbstractAutoProxyCreator中有两个方法：
 * 一个是getAdvicesAndAdvisorsForBean()，该方法非常重要，会返回所有的advices（读取BeanDefinition，返回用到Aop的方法的相关信息）；
 * 另一个是createProxy()，也就是创建代理对象，
 * 如果targetClass.isInterface()就创建JdkDynamicAopProxy(通过Proxy.newProxyInstance(代理类，接口集合，InvocationHandler对象))，否则创建CglibAopProxy
 * （spring的动态代理只能为接口创建代理实例，从Proxy.newProxyInstance的第二个参数就能看出来）
 */
@Slf4j
public class DynamicProxy implements InvocationHandler {
    //AbstractAutoProxyCreator

    private Object sub;

    public DynamicProxy() {
    }

    public DynamicProxy(Object obj) {
        sub = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(method, args);
        method.invoke(sub, args);
        after(method, args);
        return null;
    }

    public void before(Method method, Object[] args) {
        log.info(" before calling method={},args={}", method, args);
    }

    public void after(Method method, Object[] args) {
        log.info(" before calling method={},args={}", method, args);
    }
}
