import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frank.entity.mysql.IncomeStatement;
import com.frank.entity.mysql.User;
import com.frank.enums.Common;
import com.frank.exception.BizException;
import com.frank.exception.ResubmitException;
import com.frank.model.JsonResult;
import com.frank.other.Node;
import com.frank.other.SingleTon;
import com.frank.repository.mysql.IncomeStatementRepository;
import com.frank.util.GenerateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
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

    private static final java.time.format.DateTimeFormatter YYYYMMDD = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd");

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
    public void test222() {

        byte b1 = 127;
        byte b2 =-128;


        User u = User.generateUser();
        u.setCreateAt(new Date());
        final String s = JSON.toJSONString(u);

        log.info("s={}", s);

        String str = "{\"age\":59,\"createAt\":1550854532191,\"phone\":17545043614,\"updateAt\":1550554532191,\"userName\":\"ceshi2125095626\",\"userType\":1}";

        final User user = JSON.parseObject(str, User.class);

        log.info("user={}", user);

    }

    @Test
    public void test2() {
        Long c = -128L;
        Long d = -128L;
        log.info("c == d ?={}", c == d);
        String s = "12345678";
        if (c.equals(s)) {
            log.info("true");
        } else {
            log.info("false");
        }

        log.info("substring={}", s.substring(0));

        final HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        ArrayList l;
        final Timestamp TIME = new Timestamp(1557072000000L);
        log.info("TIME={}", TIME);

        final java.time.format.DateTimeFormatter FORMATTER = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        log.info("FORMATTER={}",FORMATTER.format(LocalDateTime.now()));



    }

    @Test
    public void testBinary2Searc3h32() {
//        final User.Builder userBuilder = new User.Builder().age(18);
//        userBuilder.phone(13291790987L);
//        userBuilder.userType(1);
//        userBuilder.userName("建造者模式");
//        final User user = userBuilder.bulid();
//        log.info("user={}", user);

        Long id = 123L;
        Long t = null;
        if (id.equals(t)) {
            log.info("true");
        } else {
            log.info("false");
        }

        final User user = User.generateUser();
        final String s = JSON.toJSONString(user);
        log.info("user String = {}", s);

        Object obj = s;
        if (obj instanceof User) {
            log.info("obj instanceof User ");
        } else {
            log.info("obj not instanceof User ");
        }

    }

    @Test
    public void testBinary2Searc3h2() {
        AtomicReference<User> ATOMIC_REFERENCE = new AtomicReference<User>();

        User u1 = null;
        User u = User.generateUser();
        u.setAge(123);
        u.setUserName("ceshi1234");
        try {
            if (u.getAge() == 123) {
                throw new BizException(Common.REQUEST_AT);
            }
        } catch (BizException e) {
            log.info("code={},errorMsg={},e={}", e.getCode(), e.getMessage(), ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        final User user = Optional.ofNullable(u1).orElse(u);
        log.info("user={}", user);


//        User u2 =u;
//        log.info("u2.equals(u) ={}",u2.equals(u));
//        u2.setAge(99);
//      //  u.setAge(91);
//        log.info("after set age u2.equals(u) ={}",u2.equals(u));
//        final boolean b = ATOMIC_REFERENCE.compareAndSet(u, u2);
//        String s = "12ss";
//
//
//        log.info("result = {}",b);

    }

    @Test
    public void testBinary2Search2() {
//        User user = User.generateUser();
//       // user.setTest(null);
//        final Integer integer = Optional.ofNullable(user)
//                .map(u -> u.getTest() !=null  && u.getAge() >10)
//                .orElse(0);

//        log.info("integer={}",integer);


        LinkedList l = new LinkedList();
        String str = "a";
        final String substring = str.substring(0, str.length() - 1);
        log.info("substring:{}", substring);

        final byte[] bytes = str.getBytes();
        log.info("bytes={}", bytes);
        if (str.length() == 0) {
            log.info(" length  == 0");
        } else {
            log.info(" length  ={}", str.length());
        }

        Map<String, String> map = new HashMap<>(10);
        map.put("area", "1234平米");
        map.put("duration", "3年");
        map.put("employeeNumber", "1009");
        map.put("businessVolume", "营业额6879万元?");
        map.put("bills", "流水69万元?");
        log.info(":map={}", JSON.toJSONString(map));
    }

    @Test
    public void testBinarySearch2() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        User u = User.generateUser();
        u.setUserName(list.toString());

        // final String s = list.toString();
        log.info("user={}", u);
        log.info("s={}", StringUtils.join(list, ","));

        log.info("user1111111111");
        try {
            method1(2);
        } catch (Exception e) {
            log.info("e={}", e.getMessage());
        }


        log.info("user222222222221111111111");

    }

    private void method1(int m) throws IllegalArgumentException {
        if (m == 1) {
            throw new IllegalArgumentException();
        }

        log.info("33333333333");
        if (m == 2) {
            throw new ResubmitException("1234");
        }
    }


    /**
     * 二分查找
     */
    @Test
    public void testBinarySearch() {
        int[] numbers = init(100);
        int n = 110;
        final int index = getIndex(numbers, n);
        log.info("index={}", index);

    }

    private int getIndex(int[] numbers, int num) {
        int head = 0;
        int tail = numbers.length - 1;
        if (numbers[head] > num || numbers[tail] < num) {
            throw new RuntimeException("未找到数据");
        }
        while (tail > head) {
            int mid = (head + tail) / 2;
            int currentValue = numbers[mid];
            if (currentValue == num) {
                return mid;
            }

            if (currentValue < num) {
                head = mid + 1;
            } else {
                tail = mid - 1;
            }

        }
        throw new RuntimeException("未找到数据");
    }

    private int[] init(int number) {
        if (number <= 0) {
            number = 10;
        }
        int[] b = new int[number];
        for (int i = 0; i < number; i++) {
            b[i] = i;
        }
        return b;
    }

    @Test
    public void testForityBillion() {
        final int i1 = BigDecimal.ZERO.compareTo(new BigDecimal("123"));
        String str = "S123.00";

        log.info("bigDecimal1={}", i1);
//        if(!NumberUtils.isNumber(str)){
//            log.info("not number");
//        }else {
//            log.info("number");
//        }
//        final BigDecimal bigDecimal = NumberUtils.createBigDecimal(str);


        String response = "sdfsd";
        String userName = "账单李四王五照料";
        if (userName.length() > 4) {
            userName = userName.substring(0, 4).concat("...");
        }
        log.info("userName={}", userName);
        JsonResult jsonResult = JSONObject.parseObject(response, JsonResult.class);
        log.info("jsonResult={}", jsonResult);
        log.info("jsonResult== null{}", jsonResult == null);

        final LocalDate now = LocalDate.now();
        final long l = now.atStartOfDay().minusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info("now={}", l);

        log.info("time={}", new Date(l));

        String test = null;
        final Optional<String> test1 = Optional.ofNullable(test);
        if (test1.isPresent()) {
            log.info("true");
        } else {
            log.info("false");
        }

        String contractNo = "sadfjlkasdfjabc.pdf";
        final int i = contractNo.lastIndexOf(".");

        final String substring = contractNo.substring(0, contractNo.lastIndexOf("."));
        log.info("substring={}", substring);
        String idCard = "110111199809092345";
        byte[] b = new byte[0];
        final Optional<byte[]> b1 = Optional.ofNullable(b);
        log.info("b1={}", b1.get());
        log.info("b1.length={}", b1.get().length);
//       byte[] numbers = new byte[40*1000*1000*1000];
//       numbers[0] = 0;
//        numbers[1] = 0;
//        numbers[2] = 0;
//        numbers[3] = 1;
//        numbers[4] = 1;
//        numbers[5] = 1;
//        numbers[6] = 0;
//        numbers[7] = 0;
//        numbers[8] = 0;
//        numbers[9] = 0;
//
//     //  numbers.s
//        log.info("exist={}",numbers[5]);


    }

    @Test
    public void testRateLimiter() {
        /**
         * 每秒产生的令牌数量
         * permitsPerSecond
         */
        RateLimiter rateLimiter = RateLimiter.create(2.0);

        if (rateLimiter.tryAcquire()) { //未请求到limiter则立即返回false
            // doSomething();
        } else {
            //doSomethingElse();
        }
    }

    /**
     * 限制线程池的执行速度
     * final RateLimiter rateLimiter = RateLimiter.create(5000.0);
     *
     * @param tasks
     * @param executor
     */

    void submitTasks(List<Runnable> tasks, Executor executor) {
        for (Runnable task : tasks) {
            //   rateLimiter.acquire(); // may wait
            executor.execute(task);
        }
    }

    /**
     * 每发送N字节的数据，使用N个令牌
     * 假如我们会产生一个数据流,然后我们想以每秒5kb的速度发送出去.
     * 我们可以每获取一个令牌(permit)就发送一个byte的数据,这样我们就可以通过一个每秒5000个令牌的RateLimiter来实现:
     *
     * @param packet
     */
    void submitPacket(byte[] packet) {
        //   rateLimiter.acquire(packet.length);
        // xxService.doSomething(packet);
    }


    @Test
    public void testMap2() {
        String s = "123年的ll";
        String e1 = s.substring(0, s.indexOf("年"));
        log.info("e1={}", e1);
        Integer a = 200;
        Integer b = 200;
        int c = 200;
        log.info("Integer Integer result = {}", a == b);
        log.info("Integer int result = {}", a == c);
        Integer d = 20;
        Integer e = 20;
        log.info("Integer  -128~127之间，result = {}", d == e);
        Hashtable t;
        HashMap m;
        // 2018-08-24 15:08:03
        Date deadline = new Date(1535094483000L);
        final Date date = DateUtils.addDays(deadline, -30);
        log.info("deadline={},date={}", deadline, date);
    }


    /**
     * 链表反转
     */
    @Test
    public void testReverseNode() {
        Node n5 = new Node(5, null);
        Node n4 = new Node(4, n5);
        Node n3 = new Node(3, n4);
        Node n2 = new Node(2, n3);
        Node n1 = new Node(1, n2);

        Node head = n1;
        while (head != null) {
            log.info("tmp data={}", head.toString());
            head = head.getNext();
        }


        head = n1;
        Node pre = reverse(head);

        while (pre != null) {
            log.info("tmp data={}", pre.getData());
            pre = pre.getNext();
        }


    }


    /**
     * 判断一个单向链表是不是有环
     * 思路：两个人跑步，能套圈则说明有环
     */
    @Test
    public void isCycle() {
        Node n5 = new Node(5, null);
        Node n4 = new Node(4, n5);
        Node n3 = new Node(3, n4);
        Node n2 = new Node(2, n3);
        Node n1 = new Node(1, n2);

        n5.setNext(n1);

        final boolean cycle = isCycle(n1);
        log.info("cycle={}", cycle);
    }

    /**
     * 永远停不下来怎么办
     *
     * @param node
     * @return
     */
    private boolean isCycle(Node node) {


        if (node == null) {
            throw new RuntimeException("参数异常");
        }

        long beginAt = System.currentTimeMillis();

        Node fast = node.getNext();
        Node slow = node;

        while (!fast.equals(slow)) {
            /**
             * fast 不为null，多走一步
             */
            if (fast != null) {
                fast = fast.getNext();
            } else {
                return false;
            }
            fast = fast.getNext();
            slow = slow.getNext();
            /**
             * 模拟链表过长，StackOverflowError
             */
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - beginAt > 1000 * 2) {
                throw new StackOverflowError("链表巨长,深度超出");
            }
        }

        return true;
    }

    /**
     * 找到链表的倒数第K个元素
     * 思路：记录节点M，M在当前节点之前，与当前节点相差（K-1）个节点。
     * 当遍历完成，即当前节点的next为null时，M即为倒数第K个节点
     */
    @Test
    public void findTheKthFromEnd() {
        final Node node = initNode(3);
        log.info("uuid={}", UUID.randomUUID().toString());
        Node head = node;
        while (head != null) {
            log.info("tmp data={}", head.getData());
            head = head.getNext();
        }
        final Node theKthFromEnd = getTheKthFromEnd(node, 2);
        log.info("theKthFromEnd={}", theKthFromEnd.getData());


    }


    private Node getTheKthFromEnd(Node node, int k) {
        if (node == null) {
            throw new RuntimeException("链表为空");
        }

        if (k <= 0) {
            throw new RuntimeException("参数异常");
        }


        Node head = node;
        /**
         * 在当前节点之前，与当前节点相差K个元素的节点
         * 默认是头节点
         */
        Node kThBeforeCurrent = head;
        int count = 0;
        while (head != null) {
            head = head.getNext();

            /**
             * 当前指针后移次数大于K，则kThBeforeCurrent和当前指针同步后移
             */
            if (++count > k) {
                kThBeforeCurrent = kThBeforeCurrent.getNext();
            }
            if (head == null) {
                if (count >= k) {
                    return kThBeforeCurrent;
                } else {
                    throw new RuntimeException("链表长度小于" + k);
                }
            }
        }
        return null;
    }

    private Node initNode(int number) {
        if (number < 0) {
            number = 10;
        }
        Node end = new Node(-99, null);
        Node temp = null;
        for (int i = 0; i < number; i++) {
            temp = new Node(i, end);
            end = temp;
        }
        return temp;

    }

    private Node reverse(Node head) {
        Node pre = null;
        Node current = head;
        while (current != null) {
            Node next = current.getNext();
            current.setNext(pre);
            pre = current;
            current = next;
        }
        return pre;
    }

    private static final long THIRTY_DAY = 2592000000L;
    public static final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static class Mytimer implements io.netty.util.TimerTask {

        @Override
        public void run(Timeout timeout) throws Exception {
            System.out.println("timeout=" + timeout);
            System.out.println("Mytimer task1 :" + LocalDateTime.now().format(formatter));


        }
    }

    @Test
    public void testTime() {
        String idCard = "110111199809092345";
        final LocalDate birth1 = LocalDate.parse(idCard.substring(6, 14), YYYYMMDD);
        log.info("birth1={}", birth1);
        String s = "19920114";
        final LocalDate birth = LocalDate.parse(s, YYYYMMDD);
        final Period period = birth.until(LocalDate.now());
        log.info("period={}", period);
        log.info("age={}", period.getYears());

        final LocalDate start = LocalDate.of(1992, 10, 14);
        final LocalDate end = LocalDate.of(2018, 10, 13);
        final Period until = start.until(end);

        log.info("age={}", until.getYears());


    }

    @Test
    public void testOthers2w() {
        List<Long> ids = new ArrayList<>(10);
        ids.add(1L);
        ids.add(12L);
        ids.add(13L);
        String s = "[1, 12, 13]";
        final String join = StringUtils.join(ids, ",");
        log.info("join={}", join);

        String b = "3702821992090912334534";
        final String substring = new StringBuilder(b.substring(6, 10)).append("-").append(b.substring(10, 12)).append("-").append(b.substring(12, 14)).toString();
        log.info("substring={}", substring);


        String str = "1234567";
        Integer intStr = 1234567;
        final boolean equals = str.equals(intStr);
        final boolean equals1 = intStr.equals(str);

        log.info("equals={},equals1={}", equals, equals1);

        String d = "2018-09-09";
        final Date date;
        try {
            date = DateUtils.parseDate(d, "yyyy-MM-dd");
            log.info("Date={}", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    /**
     * netty时间轮算法实现
     * 同一个时间点的任务时串行执行的
     */
    @Test
    public void e22() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        //vexecutor
        final ThreadFactory threadFactory = Executors.defaultThreadFactory();
        final io.netty.util.Timer timer = new HashedWheelTimer(executor, 5, TimeUnit.SECONDS, 2);

        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(executor, 100, TimeUnit.MILLISECONDS);
        log.info("start:{}", LocalDateTime.now().format(formatter));

        hashedWheelTimer.newTimeout(timeout -> {
            log.info("threadId={},task1 ={}", Thread.currentThread().getId(), LocalDateTime.now().format(formatter));
            Thread.sleep(2000L);
        }, 2, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout(timeout -> {
            log.info("threadId={},task2 ={}", Thread.currentThread().getId(), LocalDateTime.now().format(formatter));
            Thread.sleep(1000L);
        }, 2, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout(timeout -> {
            log.info("threadId={},task3 ={}", Thread.currentThread().getId(), LocalDateTime.now().format(formatter));
        }, 2, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout(timeout -> {
            log.info("threadId={},task4 ={}", Thread.currentThread().getId(), LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);
        log.info("mytimer 线程阻塞?= {}", LocalDateTime.now().format(formatter));

        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //log.info();
    }


    @Test
    public void testOthers2() {
        final int nano = LocalDateTime.now().minusDays(30).getNano();

        final Timestamp timestamp = new Timestamp(System.currentTimeMillis() - THIRTY_DAY);
        log.info("nano={}", nano);
        log.info("timestamp={}", timestamp);
        try {
            Class<?> commonTest = Class.forName("CommonTest");
            log.info("Class={}", commonTest);

            ClassLoader classLoader = CommonTest.class.getClassLoader();
            log.info("classLoader={}", classLoader);
            /**
             * classLoader.loadClass("XXClassName")
             * 只将class文件加载到JVM，不会执行static代码块
             */
//            Class<?> classTest = classLoader.loadClass("ClassTest");
//            log.info("classTest={}",classTest);

            /**
             * Class.forName("XXClassName");
             * 会将class文件加载到JVM，并执行static代码块
             *
             * Class.forName("ClassTest", false, classLoader);
             * 可以指定是否执行static代码块
             *
             */
//            Class<?> classTest = Class.forName("ClassTest");
//            log.info("classTest={}",classTest);

            Class<?> classTest1 = Class.forName("ClassTest", true, classLoader);
            log.info("classTest1={}", classTest1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        log.info("count1={}", SingleTon.count1);
        log.info("count2={}", SingleTon.count2);

    }

    @Test
    public void testOthers() {

        List<Long> PING_AN_CHANNELS = Lists.newArrayList(159384L, 159483L);
        long createFrom = 159384L;
        final boolean contains = PING_AN_CHANNELS.contains(Long.valueOf(createFrom));
        log.info("contains={}", contains);
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
        ConcurrentLinkedQueue q;
        SynchronousQueue q1;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
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
