import com.frank.enums.Common;
import com.frank.interceptor.LogBeforeAdvice;
import com.frank.interceptor.TimeAfterAdvice;
import com.frank.interceptor.TimeBeforeAdvice;
import com.frank.designpattern.proxy.CglibProxy;
import com.frank.designpattern.proxy.DynamicProxy;
import com.frank.designpattern.proxy.DynamicProxy2;
import com.frank.designpattern.proxy.MyProxyFactory;
import com.frank.service.DemoService;
import com.frank.service.impl.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 5:10
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication
//@ComponentScan(basePackages = "com.frank")
//@SpringBootTest(classes = ProxyTest.class)
@Rollback(false)
@Slf4j
public class ProxyTest {

    @Test
    public void testDynamicProxy2() {
        log.info(Common.LOG_BEGIN.getValue());

        /**
         * 将要被代理的目标对象
         */
        DemoServiceImpl serviceImpl = new DemoServiceImpl();

        /**
         * 从工厂获取代理，整的和真事儿一样
         * 其实就是在里边new了一个对象返回了，封装的一小步，逼格的一大步
         */
        final DynamicProxy2 proxy2 = MyProxyFactory.createProxy2();

        /**
         * 传入目标对象，得到了“代理了目标对象”的对象
         */
        DemoService demoServiceProxy = (DemoService) proxy2.proxy(serviceImpl);

        demoServiceProxy.call();

        log.info("----------华丽的分割线-----------");

        final String str = demoServiceProxy.call("测试代理");
        log.info("call str = {}", str);

        final Class<? extends DemoService> clazz = demoServiceProxy.getClass();
        log.info("demoServiceProxy.getClass()={}", clazz);
        log.info("demoServiceProxy.isInterface()={}", clazz.isInterface());

        log.info(Common.LOG_END.getValue());
    }

    @Test
    public void testCglibProxy() {

        final CglibProxy proxy = MyProxyFactory.cresteCglibProxy();
        final DemoService demoService = (DemoService) proxy.proxy(DemoServiceImpl.class);
        demoService.call();

        log.info("----------华丽的分割线-----------");

        final String str = demoService.call("测试代理");
        log.info("call str = {}", str);

        final Class<? extends DemoService> clazz = demoService.getClass();
        log.info("demoServiceProxy.getClass()={}", clazz);
        log.info("demoServiceProxy.isInterface()={}", clazz.isInterface());

        log.info(Common.LOG_END.getValue());


//        ConcurrentService concurrentService = (ConcurrentService) proxy.proxy(ConcurrentService.class);
//        log.info("concurrentService.getClass()={}", concurrentService.getClass());
//        final Integer number = concurrentService.getNumber();
//        log.info("number={}", number);
    }

    @Test
    public void testSpringDynamicProxy() {
        //Spring提供的代理工厂
        ProxyFactory factory = new ProxyFactory();
        DemoService demoService = new DemoServiceImpl();
        //指定JdkDynamicAopProxy进行代理；
        factory.setInterfaces(demoService.getClass().getInterfaces());
        factory.setTarget(demoService);
        factory.addAdvice(0, new TimeBeforeAdvice());
        factory.addAdvice(1, new LogBeforeAdvice());
        factory.addAdvice(new TimeAfterAdvice());

        final DemoService proxy = (DemoService) factory.getProxy();
        proxy.call("testSpringDynamicProxy");
        // proxy class=class com.sun.proxy.$Proxy166
        log.info("proxy class={}", proxy.getClass());
    }

    @Test
    public void testSpringCglibProxy() {
        //Spring提供的代理工厂
        ProxyFactory factory = new ProxyFactory();
        DemoService demoService = new DemoServiceImpl();
        //指定JdkDynamicAopProxy进行代理；
        factory.setInterfaces(demoService.getClass().getInterfaces());
        factory.setTarget(demoService);
        /**
         * 虽然指定了代理的接口，但由于setOptimize(true)，所以还是使用了Cglib2AopProxy代理
         */
        factory.setOptimize(true);
        factory.addAdvice(0, new TimeBeforeAdvice());
        factory.addAdvice(1, new LogBeforeAdvice());
        factory.addAdvice(new TimeAfterAdvice());
        final DemoService proxy = (DemoService) factory.getProxy();
        proxy.call("testSpringDynamicProxy");
        // 输出结果proxy class=class com.frank.service.impl.DemoServiceImpl$$EnhancerBySpringCGLIB$$35c57a8e
        log.info("proxy class={}", proxy.getClass());


    }


    /**
     * 之前写的方法，封装的不好
     */
    @Test
    public void testDynamicProxy() {
        log.info(Common.LOG_BEGIN.getValue());
        DemoServiceImpl serviceImpl = new DemoServiceImpl();
        Class cls = serviceImpl.getClass();

        log.info("DemoServiceImpl classLoader={},interfaces={}", cls.getClassLoader(), cls.getInterfaces());

        /**
         * 下边的两行代码在DynamicProxy2里做了封装，看起来更简洁明了
         */
        InvocationHandler invocationHandler = new DynamicProxy(serviceImpl);
        // 抽象角色：真实对象和代理对象的共同接口
        DemoService service = (DemoService) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), invocationHandler);
        service.call();
        service.call("测试代理");
        final Class<? extends DemoService> clazz = service.getClass();
        log.info("---------------------subject.getClass()={}", clazz);
        log.info("---------------------subject.getClass().isInterface()={}", clazz.isInterface());

        log.info(Common.LOG_END.getValue());
    }
}
