package concurrent;

import com.frank.concurrent.FinalTest;
import com.frank.service.ConcurrentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/1 0001 下午 1:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages ="com.frank")
@SpringBootTest(classes= FutureTest.class)
@Rollback(false)
@Slf4j
public class FutureTest {

    @Autowired
    private ConcurrentService concurrentService;

    // 基本数据类型，用final修饰，值不可变。
    // 引用类型，用final修饰，指向的对象可以修改，不能指向新的对象
    @Test
    public void t() {
        FinalTest f1 = new FinalTest();
        FinalTest f2 = new FinalTest();
        FinalTest f3 = new FinalTest();
        log.info("f1.STR={}", f1.STR);
        log.info("f2.STR={}", f2.STR);
        log.info("f3.STR={}", f3.STR);

        log.info("STATIC_STR={}", f1.STATIC_STR);
        log.info("STATIC_STR={}", f2.STATIC_STR);
        log.info("STATIC_STR={}", f3.STATIC_STR);
        String curStr = "World";
        StringBuilder builder = new StringBuilder("Hello");
        f1.append( builder,curStr);
        log.info("builder={}", builder);
    }


    /**
     * ThreadPoolTaskExecutor 线程池测试
     */
    @Test
    public void testS() {

        log.info("---  just for test begin");
        for (int i =0;i<50;i++){
            final Integer number = concurrentService.getNumber();
        }

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }


    /**
     * 返回值为Future的多线程测试
     */
    @Test
    public void testS2() {

        log.info("---  just for test begin");
        List<Future<String>> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Future<String> future = concurrentService.testFuture(String.valueOf(RandomUtils.nextInt(1000,7000)));
            list.add(future);
        }

        log.info("$$$$$$$$$$$$$$$$$$$$$$$ list.size()={}",list.size());

        list.stream().forEach(future ->{
            try {
                String str = future.get(40, TimeUnit.MILLISECONDS);
                log.info("----------------str={}", str);
            } catch (Exception e) {
                final boolean cancel = future.cancel(true);
                log.error("cancel={}", cancel);
            }
        });


        Map m =new HashMap();
        Hashtable h=new Hashtable();
        PriorityQueue q = new PriorityQueue();


      /*  for (int i=0;i<list.size();i++){
            Future<String> future = list.get(i);
            try {
                String str = future.get(40, TimeUnit.MILLISECONDS);
                log.info("----------------str={}", str);
            } catch (Exception e) {
                final boolean cancel = future.cancel(true);
                log.error("cancel={},i={}", cancel,i);
            }
        }
*/
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---  just for test end");

    }
}
