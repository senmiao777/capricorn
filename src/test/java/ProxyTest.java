import com.frank.enums.Common;
import com.frank.proxy.Subject2;
import com.frank.proxy.impl.DynamicSubject;
import com.frank.proxy.impl.RealSubject;
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
        InvocationHandler invocationHandler = new DynamicSubject(rs);
        Class cls = rs.getClass();

        // 抽象角色：真实对象和代理对象的共同接口
        Subject2 subject = (Subject2) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), invocationHandler);
        subject.call();
        subject.call("测试代理");
        log.info("---------------------subject={}", subject.getClass());
        log.info(Common.LOG_END.getValue());
    }
}
