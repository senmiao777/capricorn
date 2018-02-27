import com.frank.entity.mysql.IncomeStatement;
import com.frank.repository.mysql.IncomeStatementRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author frank
 * @version 1.0
 * @date 2018/2/4 0021 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = StockTest.class)
@Rollback(false)
@Slf4j
public class CommonTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;


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
