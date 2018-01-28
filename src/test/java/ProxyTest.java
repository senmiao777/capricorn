import com.frank.enums.Common;
import com.frank.service.Subject2;
import com.frank.proxy.DynamicProxy;
import com.frank.service.impl.RealSubject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void testProxy() {
        log.info(Common.LOG_BEGIN.getValue());
        RealSubject rs = new RealSubject();
        Class cls = rs.getClass();

        InvocationHandler invocationHandler = new DynamicProxy(rs);
        // 抽象角色：真实对象和代理对象的共同接口
        Subject2 subject = (Subject2) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), invocationHandler);
        subject.call();
        subject.call("测试代理");
        final Class<? extends Subject2> clazz = subject.getClass();
        log.info("---------------------subject.getClass()={}", clazz);
        log.info("---------------------subject.getClass().isInterface()={}", clazz.isInterface());

        log.info(Common.LOG_END.getValue());
    }
}
