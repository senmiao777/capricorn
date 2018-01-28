import com.frank.enums.Common;
import com.frank.proxy.CglibProxy;
import com.frank.proxy.DynamicProxy;
import com.frank.service.ConcurrentService;
import com.frank.service.DemoService;
import com.frank.service.impl.DemoServiceImpl;
import com.sun.deploy.util.Waiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 5:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = ProxyTest.class)
@Rollback(false)
@Slf4j
public class ProxyTest {

    @Test
    public void testDynamicProxy() {
        log.info(Common.LOG_BEGIN.getValue());
        DemoServiceImpl serviceImpl = new DemoServiceImpl();
        Class cls = serviceImpl.getClass();

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

    @Test
    public void testCglibProxy(){
        CglibProxy proxy = new CglibProxy();
        ConcurrentService concurrentService =(ConcurrentService)proxy.getProxy(ConcurrentService.class);
        log.info("concurrentService.getClass()={}",concurrentService.getClass());
        final Integer number = concurrentService.getNumber();
        log.info("number={}",number);
    }

    @Test
    public void testSpringDynamicProxy(){
        //Spring提供的代理工厂
        ProxyFactory factory = new ProxyFactory();
        factory.setInterfaces(waiter.getClass().getInterfaces());

　　//以上代码就指定了JdkDynamicAopProxy进行代理；

        BeforeAdvice advice = new GreetingBeforeAdvice();
        Waiter waiter  = new NaiveWaiter();

        //Spring提供的代理工厂
        ProxyFactory factory2 = new ProxyFactory();
        factory2.setInterfaces(waiter.getClass().getInterfaces());
        factory2.setOptimize(true);

        以上代码虽然指定了代理的接口，但由于setOptimize(true)，所以还是使用了Cglib2AopProxy代理；
    }

    @Test
    public void testSpringCglibProxy(){
        //Spring提供的代理工厂
        ProxyFactory factory = new ProxyFactory();
        factory.setInterfaces(waiter.getClass().getInterfaces());

　　//以上代码就指定了JdkDynamicAopProxy进行代理；

        BeforeAdvice advice = new GreetingBeforeAdvice();
        Waiter waiter  = new NaiveWaiter();

        //Spring提供的代理工厂
        ProxyFactory factory2 = new ProxyFactory();
        factory2.setInterfaces(waiter.getClass().getInterfaces());
        factory2.setOptimize(true);

        以上代码虽然指定了代理的接口，但由于setOptimize(true)，所以还是使用了Cglib2AopProxy代理；
    }
}
