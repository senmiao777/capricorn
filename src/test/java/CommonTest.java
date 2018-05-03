import com.alibaba.fastjson.JSON;
import com.frank.entity.mysql.IncomeStatement;
import com.frank.entity.mysql.User;
import com.frank.repository.mysql.IncomeStatementRepository;
import com.frank.util.GenerateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
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

    /**
     * 罗马数字与值对应关系
     */
    private static final Map<Character, Integer> ROMAN_VALUE_MAP = new HashMap<>();

    /**
     * 罗马数字减法规则
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     */
    private static final Map<Character, Character> RULE_MAP = new HashMap<>();

    //int millis = 30*24*60*60*1000;
    static {
        ROMAN_VALUE_MAP.put('I', 1);
        ROMAN_VALUE_MAP.put('V', 5);
        ROMAN_VALUE_MAP.put('X', 10);
        ROMAN_VALUE_MAP.put('L', 50);
        ROMAN_VALUE_MAP.put('C', 100);
        ROMAN_VALUE_MAP.put('D', 500);
        ROMAN_VALUE_MAP.put('M', 1000);

        RULE_MAP.put('V', 'I');
        RULE_MAP.put('X', 'I');

        RULE_MAP.put('L', 'X');
        RULE_MAP.put('C', 'X');

        RULE_MAP.put('D', 'C');
        RULE_MAP.put('M', 'C');
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;

    private String PASS = "PASS";
    private String FAIL = "FAIL";


    @Test
    public void roman2int(){
        String roman = "MCMXCIV";
        int length = roman.length();
        Character currentCharacter = null;
        Integer currentValue = null;
        // 一边遍历一边往map里放
        for(int i = 0 ; i<length;i++){
            currentCharacter = roman.charAt(i);
            currentValue = ROMAN_VALUE_MAP.get(currentCharacter);

        }
        //roman.charAt()
    }



    enum Suit {
        CLUB, DIAMOND
    }

    enum Rank {
        ACE, DEUCE, THREE
    }

    @ToString
    static class Card {
        private Suit suit;
        private Rank rank;

        Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }
    }


    /**
     * 迭代器测试
     */
    @Test
    public void testCollection() {
        List<Suit> suits = Arrays.asList(Suit.values());
        List<Rank> ranks = Arrays.asList(Rank.values());
        List<Card> deck = new ArrayList<>();
        for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
            final Suit next = i.next();
            final Iterator<Rank> iterator = ranks.iterator();
            for (; iterator.hasNext(); ) {
                deck.add(new Card(next, iterator.next()));
            }
        }
        log.info("deck={}", deck);
    }

    @Test
    public void timestamp() {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        log.info("time={}", timestamp);
        long i = 30L * 24L * 60L * 60L * 1000L;
        log.info("i ={}", i);
        final long currentTimeMillis = System.currentTimeMillis();
        Timestamp localDateTime = new Timestamp(currentTimeMillis + i);

        log.info("30 timestamp1={}", localDateTime);

    }

    class Vo {
        String amount;
        List<Integer> list;

    }

    class Time {
        private Date start;
        private Date end;

        public Time(Date start, Date end) {
            /**
             * TODO
             * 保护性拷贝
             */
            if (start.getTime() > end.getTime()) {
                try {
                    throw new Exception("数据异常");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Time{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
    }

    @Test
    public void unsafeCopy() {
        Date start = new Date();
        Date end = new Date();
        Time time = new Time(start, end);
        log.info("time={}", time);
        end.setYear(100);
        log.info("1524392629000 time={}", time);
    }

    @Test
    public void testMap() {
        Map<String, List<Integer>> map = new HashMap<>(5);
        List<Integer> one = Lists.newArrayList(1, 2, 3);
        List<Integer> two = Lists.newArrayList(1, 2, 3);
        map.put("2000", one);
        map.put("4000", two);
        log.info("map={}", JSON.toJSONString(map));

        Map<String, Set<Integer>> map2 = new HashMap<>(5);
        Set<Integer> one2 = Sets.newHashSet(1, 2, 3, 2);
        Set<Integer> two2 = Sets.newHashSet(1, 2, 3);
        map2.put("2000", one2);
        map2.put("4000", two2);
        log.info("map={}", JSON.toJSONString(map2));
    }

    @Test
    public void testDiff() {

        //TimerWheel t= null;

        Set<String> bigSister = Sets.newHashSet("SP100048313941366128910653",
                "SP100890807501582539332578",
                "SP100037052451357347870258",
                "SP100006291951332772831013",
                "SP999407319912734193182643",
                "SP997341996010969304572867",
                "SP100825076691606646049932",
                "SP100823686721592163394610",
                "SP997161108710796669561233",
                "SP100821935731579566619780",
                "SP992828282410671523593759",
                "SP100817290351542216662539",
                "SP100781104611487998545094",
                "SP100626160601427377228474",
                "SP100603522361397208405850",
                "SP974649527072358897510906");

        Set<String> mine = Sets.newHashSet("SP100603522361397208405850",
                "SP100626160601427377228474",
                "SP100781104611487998545094",
                "SP100817290351542216662539",
                "SP100821935731579566619780",
                "SP100823686721592163394610",
                "SP100825076691606646049932",
                "SP100890807501582539332578");

        log.info("removeAll={}", bigSister.removeAll(mine));
        log.info("removeAll={}", bigSister);

        //   log.info("containsAll={}",bigSister.containsAll(mine));

    }

    @Test
    public void testBigDicimal() {
        Character c = ' ';
        String s = " ";

        log.info("result={}", c.equals(s.charAt(0)));

        BigDecimal amount = new BigDecimal("123");
        log.info("amount={}", amount);
        final BigDecimal bigDecimal = amount.setScale(6, RoundingMode.HALF_UP);
        log.info("bigDecimal={}", bigDecimal);

        log.info("bigDecimal={}", bigDecimal.multiply(new BigDecimal("30")));
    }

    @Test
    public void testSPEL() {
        final String spel = "#this[0]+':'+#this[1]";

        Expression expression = new SpelExpressionParser().parseExpression(spel);
        Object[] args = new Object[2];
        args[0] = "sudfsd";
        args[1] = 12L;
        String value = expression.getValue(args, String.class);
        log.info("value={}", value);
        Expression expression2 = new SpelExpressionParser().parseExpression("4>0");
        Boolean value2 = expression2.getValue(Boolean.class);
        log.info("value2={}", value2);

        Map<String, String> map = new HashMap<>(3);
        map.put("k1", "v1");
        Expression expression3 = new SpelExpressionParser().parseExpression("#this.get('k1')");
        String va = expression3.getValue(map, String.class);
        log.info("va={}", va);

        User user = new User();
        user.setAge(19);
        user.setUserType(1);
        //String s= "#this.userType+':'+#this.age";
        List<User> list = new ArrayList<>(1);
        list.add(user);
        // String s= "#this.userType+':'+#this.age";
        String s = "#this[0].userType+':'+#this[0].age";
        //  String s= "#this.userType";
        Expression expression6 = new SpelExpressionParser().parseExpression(s);
        String v6 = expression6.getValue(list, String.class);
        log.info("v6={}", v6);
    }

    public String getKey(String uuid, Long productId) {
        String spel = "#{" + uuid + ":" + productId + "}";
        return spel;
    }

    @Test
    public void testString23() {

        String str = "暂唯一要求是身高要180以上的";

        int i = str.indexOf("要求");
        log.info("i = {}", i);
        log.info("str = {}", str.substring(i));
    }

    @Test
    public void testCharAt() {
//        String test = "abcdefg";
//        final char c = test.charAt(1);
//        log.info("charAt = {}",c);
        List<String> list = Lists.newArrayList("uuid varchar(20) DEFAULT NULL COMMENT ",
                "user_id bigint(20) DEFAULT NULL",
                "product_id bigint(20) DEFAULT NULL COMMENT '产品ID'",
                "total_amount decimal(20,2) DEFAULT '0.00' COMMENT '总额度'",
                "available_amount decimal(20,2) DEFAULT '0.00' COMMENT '可用额度'",
                "used_amount decimal(20,2) DEFAULT '0.00' COMMENT '已使用额度'",
                "freeze_amount decimal(20,2) DEFAULT '0.00' COMMENT '冻结额度'",
                "expire_at timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '失效时间（风控提供）'",
                "remark varchar(255) DEFAULT '' COMMENT '备注'",
                "is_active tinyint(1) DEFAULT '1' COMMENT '数据是否有效标志位'");

        final String s = GenerateUtil.sqlList2Entity(list);
        log.info("GenerateUtil={}", s);

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
