package concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 1:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication
//@ComponentScan(basePackages ="com.frank")
//@SpringBootTest(classes= FutureTest.class)
@Rollback(false)
@Slf4j
public class FutureTest {

    /**
     * static synchronized 修饰方法测试
     */
    @Test
    public void testS() {

        log.info("---  just for test begin");

        log.info("---  just for test end");

    }

}
