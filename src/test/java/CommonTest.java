import com.alibaba.fastjson.JSON;
import com.frank.entity.mysql.IncomeStatement;
import com.frank.repository.mysql.IncomeStatementRepository;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author frank
 * @version 1.0
 * @date 2018/2/4 0021 下午 4:18
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication
//@ComponentScan(basePackages = "com.frank")
//@SpringBootTest(classes = StockTest.class)
@Rollback(false)
@Slf4j
public class CommonTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;

    private String PASS = "PASS";
    private String FAIL = "FAIL";
    @Test
    public void testString23() {
        String str = "暂唯一要求是身高要180以上的";

        int i = str.indexOf("要求");
        log.info("i = {}",i);
        log.info("str = {}",str.substring(i));
    }

    @Test
    public void testString2() {
        String test = PASS;
        if (PASS.equals(test)) {
            log.info("testString2 PASS in ");
            test = FAIL;
        }
        if (FAIL.equals(test)) {
            log.info("testString2 FAIL in ");
        }
    }

    @Test
    public void testNullw() {

        Map<String, Boolean> map = Maps.newHashMap();
        map.put("sendMessage", false);
        log.info("map={}", JSON.toJSONString(map));
    }

    @Test
    public void testNull() {
        Object obj = null;
        Map<String, String> map = (Map<String, String>) obj;
        log.info("map={}", map);
    }

    @Test
    public void testIndexOf() {
        String between = "between";
        String between2 = "123456betweentoMinutes";
        final int i = between2.indexOf(between);
        log.info("i={}", i);
    }

    @Test
    public void testDuration() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime updatedAtPlus = now.plusMinutes(-68).plusDays(-15);
        final Duration between = Duration.between(now, updatedAtPlus);
        // final Duration between = Duration.between(updatedAtPlus,now);
        log.info("between={}", between);
        log.info("between.toDays={}", between.toDays());
        log.info("between.toHours={}", between.toHours());
        log.info("between.toMinutes={}", between.toMinutes());
        log.info("between.toMillis={}", between.toMillis());


    }


    @Test
    public void testNumberUtil() {
        String num1 = "-1";
        final boolean digits = NumberUtils.isDigits(num1);
        log.info("NumberUtils.isDigits(num1)={}", digits);
        final boolean isNumber = NumberUtils.isNumber(num1);
        log.info("NumberUtils.isNumber(num1)={}", isNumber);
    }

    @Test
    public void testStream() {
        String stockCode = "000002";
        List<IncomeStatement> stockList = incomeStatementRepository.findByStockCode(stockCode);
        log.info("stockList.size={}", stockList.size());
        BigDecimal amount = new BigDecimal("0.09");
        final List<IncomeStatement> collect = stockList.stream().filter(s -> s.getDilutedEPS().compareTo(amount) == 1).collect(Collectors.toList());

        collect.stream().forEach(
                c -> {
                    log.info("amount={}", c.getDilutedEPS());
                });
        // log.info("collect={}", collect);
    }

    @Test
    public void testFor() {
        int r;
        for (int i = 0; ; i++) {

            r = RandomUtils.nextInt(10, 100);
            log.info("i={},r={}", i, r);
            // 当随机数为7的倍数时，返回
            if (r % 7 == 0) {
                log.info("i={},r={},return", i, r);
                return;
            }
        }
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("k2", "v2");
    }

    /**
     * 由ArrayBlockingQueue 其中一个构造函数
     * public ArrayBlockingQueue(int capacity, boolean fair,Collection<? extends E> c)
     * 中用的赋值方式items[i++] = e;
     * for (E e : c) {
     * items[i++] = e;
     * }
     * //元素数量
     * count = i;
     * 引发的测试
     */
    @Test
    public void testA() {
        int a = 0;
        int c = 0;
        int b = 1;
        if (a++ == b) {
            log.info("a++ == b,a={},b={}", a, b);
        } else {
            log.info("a++ != b,a={},b={}", a, b);
        }

        if (++c == b) {
            log.info("++c == b,c={},b={}", c, b);
        } else {
            log.info("++c != b,c={},b={}", c, b);
        }
        /**
         *
         for (E e : c) {
         items[i++] = e;
         }
         // 元素数量
         count = i;
         */
        int[] items = new int[10];
        int i = 0;
        int total = 10;
        for (; i < total; ) {
            items[i++] = 1;
        }
        log.info("items[0]={}", items);
    }

    @Test
    public void testExpire() {
        String redisKey = "testPerfix:18110000002";

        ;
        log.info("[testExpire] third expire={},get={}");

    }
}
