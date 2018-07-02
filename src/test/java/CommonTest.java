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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.Format;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    enum Roman {
        M("M", 1000, "M对应值为1000"),
        D("D", 500, "D对应值为500"),
        C("C", 100, "C对应值为100。C can be placed before D (500) and M (1000) to make 400 and 900"),
        L("L", 50, "L对应值为50"),
        X("X", 10, "X对应值为10。X can be placed before L (50) and C (100) to make 40 and 90"),
        V("V", 5, "V对应值为5"),
        I("I", 1, "I对应值为1。I can be placed before V (5) and X (10) to make 4 and 9"),
        IV("IV", 4, "IV对应值为4"),
        IX("IX", 9, "IX对应值为9"),
        XL("XL", 40, "XL对应值为40"),
        XC("XC", 90, "XC对应值为90"),
        CD("CD", 400, "CD对应值为400"),
        CM("CM", 900, "CM对应值为900");

        private String key;
        private int value;
        private String desc;

        Roman(String key, int value, String desc) {
            this.key = key;
            this.value = value;
            this.desc = desc;
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;

    private String PASS = "PASS";
    private String FAIL = "FAIL";

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMATTER_RESULT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");


    @Test
    public void testOthers() {
        final String join = String.join("/", "user", "info", "id");
        log.info("join={}", join);
    }

    @Test
    public void testJDKFormatter() {
        String timeStr = "20180101";
        final TemporalAccessor parse = java.time.format.DateTimeFormatter.BASIC_ISO_DATE.parse(timeStr);
        final String s = parse.toString();
        log.info("s={}", s);

        final Format format = java.time.format.DateTimeFormatter.BASIC_ISO_DATE.toFormat();
        log.info("format={}", format);

        //java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd").


    }

    @Test
    public void tesPhone() {
        String phone = "\u202ds132404dfef1177s8s\u202cs";
        log.info("dateTime2={}", phone = phone.replaceAll("[^\\d]", ""));
        log.info("dateTime={}", isPhoneNoValid(phone));
//
//
//        final String trim = phone.trim();
//
//        log.info("dateTime2={}",isPhoneNoValid(trim));


    }

    public static boolean isPhoneNoValid(String phoneNo) {
        if (phoneNo == null) {
            return false;
        }
        String phoneNoPattern = "^1\\d{10}$";
        return Pattern.matches(phoneNoPattern, phoneNo);
    }


    private String testR(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    @Test
    public void testJoda() {
        String timeStr = "20180101";
//        final String dateTime2 = formatTime(timeStr);
//        log.info("dateTime2={}",dateTime2);
        timeStr.replaceAll("[^\\d]", "13");
    }

    private String formatTime(String time) {
        return DateTime.parse(time, FORMATTER).toString(FORMATTER_RESULT);
    }

    @Test
    public void testEquals() {
        Map<String, String> map = new HashMap<>(6);
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        for (Map.Entry<String, String> e : map.entrySet()) {
            log.info("key={},value={}", e.getKey(), e.getValue());
        }

        log.info("---华丽的分割线---");

        map.forEach((k, v) -> {
            log.info("key={},value={}", k, v);
        });
    }

    @Test
    public void threadPoolTest2() {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        class TestTask implements Callable<String> {

            @Override
            public String call() throws Exception {
                // executorService.submit()
                return null;
            }
        }

    }

    /**
     * 单线程嵌套调用，发生死锁
     */
    @Test
    public void threadPoolTest() {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);


        executorService.execute(new Runnable() {

            @Override
            public void run() {

                log.info("outter in---");
                final Future<?> submit = executorService.submit(new Runnable() {
                    @Override
                    public void run() {

                        log.info("111 in---");
                    }
                });
                final Object o;
                try {
                    o = submit.get();
                    log.info("Object={}", o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                try {
                    log.info(" outter AAAAA---");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(RandomUtils.nextInt(10, 50));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("222 in---");
                    }
                });
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(RandomUtils.nextInt(10, 50));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("333 in---");
                    }
                });
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(RandomUtils.nextInt(10, 50));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("444 in---");
                    }
                });
                log.info("outter finish---");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("main finish---");
    }


    /**
     * Input: [1,3,5,6], 5
     * Output: 2
     * <p>
     * Example 2:
     * <p>
     * Input: [1,3,5,6], 2
     * Output: 1
     * <p>
     * Example 3:
     * <p>
     * Input: [1,3,5,6], 7
     * Output: 4
     * <p>
     * Example 4:
     * <p>
     * Input: [1,3,5,6], 0
     * Output: 0
     */
    @Test
    public void findIndex() {
        //int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 9, 10, 11};
        int[] array = new int[]{1, 3, 5, 6};
        log.info("findIndex={}", position(array, 1));
    }

    private int position(int[] array, int target) {
        final int length = array.length;

        if (length == 0) {
            return 0;
        }

        /**
         * 小于最小值
         */
        if (array[0] > target) {
            return 0;
        }

        /**
         * 大于最大值
         */
        if (array[length - 1] < target) {
            return length;
        }

        int low = 0;
        int high = length - 1;
        int mid = 0;

        while (low <= high) {
            mid = (high + low) / 2;
            if (array[mid] == target) {
                return mid;
            }

            if (array[mid] < target) {
                low = mid + 1;
            }

            if (array[mid] > target) {
                high = mid - 1;
            }
        }

        if (high <= low || high <= mid) {
            return Math.max(low, mid);
        }
        return 999;
    }

    @Test
    public void roman2int() {
        String roman = "XLV";
        log.info("roman2int={}", roman2IntValue(roman));
        //roman.charAt()
    }

    private int roman2IntValue(String roman) {
        int length = roman.length();

        if (length == 0) {
            return 0;
        }

        int result = 0;

        String currentCharacter = null;
        String nextCharacter = null;

        for (int i = 0; i < length; i++) {
            currentCharacter = String.valueOf(roman.charAt(i));
            if (Roman.M.key.equals(currentCharacter)) {
                result = result + Roman.M.value;
                continue;
            }

            if (Roman.D.key.equals(currentCharacter.toString())) {
                result = result + Roman.D.value;
                continue;
            }

            if (Roman.C.key.equals(currentCharacter.toString())) {

                if (++i == length) {
                    result = result + Roman.C.value;
                    break;
                }
                nextCharacter = String.valueOf(roman.charAt(i));
                if (Roman.D.key.equals(nextCharacter)) {
                    result = result + Roman.CD.value;
                    continue;
                }

                if (Roman.M.key.equals(nextCharacter)) {
                    result = result + Roman.CM.value;
                    continue;
                }

                result = result + Roman.C.value;
                i--;

            }

            if (Roman.L.key.equals(currentCharacter.toString())) {
                result = result + Roman.L.value;
                continue;
            }

            if (Roman.X.key.equals(currentCharacter.toString())) {
                if (++i == length) {
                    result = result + Roman.X.value;
                    break;
                }
                nextCharacter = String.valueOf(roman.charAt(i));
                if (Roman.L.key.equals(nextCharacter)) {
                    result = result + Roman.XL.value;
                    continue;
                }

                if (Roman.C.key.equals(nextCharacter)) {
                    result = result + Roman.XC.value;
                    continue;
                }

                result = result + Roman.X.value;
                i--;
            }

            if (Roman.V.key.equals(currentCharacter.toString())) {
                result = result + Roman.V.value;
                continue;
            }


            if (Roman.I.key.equals(currentCharacter.toString())) {
                if (++i == length) {
                    result = result + Roman.I.value;
                    break;
                }
                nextCharacter = String.valueOf(roman.charAt(i));
                if (Roman.V.key.equals(nextCharacter)) {
                    result = result + Roman.IV.value;
                    continue;
                }

                if (Roman.X.key.equals(nextCharacter)) {
                    result = result + Roman.IX.value;
                    continue;
                }

                result = result + Roman.I.value;
                i--;
            }


        }
        return result;
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
