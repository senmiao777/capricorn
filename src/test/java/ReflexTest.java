import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author: somebody
 * @time: 2019-02-15 15:27
 */
@Slf4j
public class ReflexTest {

    /**
     * 什么不直接new出来呢?通过反射来创建对象，调用方法多费劲啊
     * 关键就是:很多时候我们并不能事先知道要new什么对象,相反,我们可能只知道一个类的名称和方法名,很多时候这些名称都是写在XML配置当中的。
     * 比如
     * <beanid="helloWorld" class="example.HelloWorld">
     *      <propertyname="message" value="Hello World!"/>
     * </bean>
     */
    @Test
    public void testReflex() {
        try {
            final Class<?> aClass = Class.forName("com.frank.service.impl.TestServiceImpl");

            final Object object = aClass.newInstance();
            log.info("object={}",object);

            final Method method = aClass.getDeclaredMethod("justTest");
            log.info("method={}",method.getClass());

            final Object invoke = method.invoke(object);
            log.info("invoke={}",invoke);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
