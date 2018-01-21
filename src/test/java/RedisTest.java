import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/21 0021 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages ="com.frank")
@SpringBootTest(classes=StockTest.class)
@Rollback(false)
@Slf4j
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("k2","v2");
    }

    @Test
    public void testStringGet(){

        Object k2 = redisTemplate.opsForValue().get("k2");
        System.out.println("result:"+k2);
        Object k3 = redisTemplate.opsForValue().get("testPerfix:18110000001");
        System.out.println("result:"+k3);
        redisTemplate.opsForValue().set("testPerfix:18110000001",0L);
        Object k6 = redisTemplate.opsForValue().get("testPerfix:18110000001");
        System.out.println("result:"+k6);
    }
}
