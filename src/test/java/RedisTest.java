import com.frank.designpattern.decorator.Beverage;
import com.frank.entity.mysql.Benefit;
import com.frank.entity.mysql.User;
import com.frank.service.IDbService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/21 0021 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = RedisTest.class)
@Rollback(false)
@Slf4j
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IDbService dbService;

    @Autowired
    @Qualifier("nothing")
    private Beverage nothingTest;

    public Boolean setBit(String key, Integer index, Boolean tag) {
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) con -> con.setBit(key.getBytes(), index, tag));
    }

    public Boolean getBit(String key, Integer index) {
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) con -> con.getBit(key.getBytes(), index));
    }

    /**
     * 统计bitmap中，value为1的个数，非常适用于统计网站的每日活跃用户数等类似的场景
     *
     * @param key
     * @return
     */
    public Long bitCount(String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, int start, int end) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }


    @Test
    public void testmachineGun() {

        log.info("what={}", nothingTest.cost());


    }

    @Test
    public void testt2() {
        User u1 = User.generateUser();
        User u2 = User.generateUser();
        User u3 = User.generateUser();
        User u4 = User.generateUser();

        List<User> userList = Lists.newArrayList(u1, u2, u3, u4);

        Benefit benefit = Benefit.generateBenefit();
        dbService.update(userList, benefit);
    }

    @Test
    public void testt() {
        User u = User.generateUser();
        Benefit benefit = Benefit.generateBenefit();
        dbService.update(u, benefit);
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("k2", "v2");
    }

    @Test
    public void testStringGet() {

        Object k2 = redisTemplate.opsForValue().get("k2");
        System.out.println("result:" + k2);
        Object k3 = redisTemplate.opsForValue().get("testPerfix:18110000001");
        System.out.println("result:" + k3);
        redisTemplate.opsForValue().set("testPerfix:18110000001", 0L);
        Object k6 = redisTemplate.opsForValue().get("testPerfix:18110000001");
        System.out.println("result:" + k6);
        /**
         * setNx ,只有当key不存在的时候，才能将key创建
         * 返回值是个布尔值，根据这个判断此时是否有并发调用
         */
        String key = "myKey";
        String value = "whatever";
        Boolean aBoolean = redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), value.getBytes());
        Boolean expire = redisTemplate.expire(key, 3000, TimeUnit.MILLISECONDS);


    }

    @Test
    public void testExpire() {
        String redisKey = "testPerfix:18110000002";

        Object first = redisTemplate.opsForValue().get(redisKey);
        log.info("[testExpire] first get={}", first);

        final Long increment = redisTemplate.opsForValue().increment(redisKey, 1L);
        log.info("[testExpire] increment={}", increment);


        Object second = redisTemplate.opsForValue().get(redisKey);
        log.info("[testExpire] second get={}", second);

        final Boolean expire = redisTemplate.expire(redisKey, 0L, TimeUnit.MILLISECONDS);

        Object third = redisTemplate.opsForValue().get(redisKey);
        log.info("[testExpire] third expire={},get={}", expire, third);

    }
}
