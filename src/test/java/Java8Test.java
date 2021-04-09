import com.frank.entity.mysql.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * @author frank
 * @version 1.0
 * @date 2018/6/20
 */
@Slf4j
public class Java8Test {

    public String[] strs = {"new", "Java8", "new", "feature", "Stream", "API"};
    public int[] intStrs = {12, 136, 22, 35, 66};

    @Test
    public void testAtomicReference() throws InterruptedException {
        AtomicReference<User> reference = new AtomicReference<>();
        User u = new User();
        u.setUserName("test1");
        reference.set(u);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i=0;i< 10;i++){
            executorService.submit(()->{
                User user = User.generateUser();
                boolean b = reference.compareAndSet(u, user);
                System.out.println("result="+b+",user="+user);
            });
        }

        User u2 = new User();
        u2.setUserName("test222");
        AtomicReference<User> atomicReference3 = new AtomicReference<>(u2);

        User u3 = new User();
        u3.setUserName("test3333");

        /**
         * 设置新值，返回老值
         */
        User simpleObject2 = atomicReference3.getAndSet(u3);

        System.out.println(simpleObject2 +"|分割线|"+atomicReference3.get());

        System.out.println("reference.user11="+reference.get());

        Thread.sleep(200L);
        System.out.println("reference.user22="+reference.get());

    }

    @Test
    public void testOptional2() {
        test(3);
    }

    private void test(int num) {
        int t = 3;
        if (num == 1) {
            t = 1;
            log.info("1111");

        } else if (num == 2) {
            t = 2;
            log.info("1111");
        } else {
            return;
        }
        log.info("here t={}", t);
    }

    @Test
    public void testOptional() {
        User u = User.generateUser();
        log.info("user = {}", u);
        final Optional<User> u1 = Optional.ofNullable(u);
        final Optional<Long> aLong = u1.map(User::getId);
        u1.ifPresent(t -> t.setUserName("ifPresent函数"));


        /**
         * 当没有值时候的处理
         */
        User u2 = null;
        final User user = Optional.ofNullable(u2).orElse(u);
        log.info("user = {}", user);

        log.info("user2 ={}", getId(u2));

        List<String> list = new ArrayList<>(20);
        final boolean add = list.add("23");

        try {
            final User user2 = Optional.ofNullable(u2).orElseThrow(Exception::new);
            Optional.ofNullable(u2).ifPresent(a -> {
            });
            // Optional.ofNullable(u2).map(a ->{log.info(":");return null;}).orElse(log.info(""););
            log.info("user2 ={}", user2);
        } catch (Exception e) {
            log.info("Exception user2 e={}", e);
        }


    }

    private Long getId(User u) {
        log.info("Optional user = {}", u);
        Optional.ofNullable(u).map(a -> {
            log.info("22");
            return null;
        }).orElse(1L

        );
        return 1L;
    }


    @Test
    public void testLambda() {
        final Stream<String> strs3 = Stream.of(strs);
        /**
         * 指定类型，否则返回值是Object[]类型
         */
        final String[] strings = strs3.toArray(String[]::new);
        //(String first,String second) -> Integer.compare(first.length(),second.length());
    }


    @Test
    public void getCount() {
        int count = 0;
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() > 3) {
                count++;
            }
        }
        log.info("字符串长度大于3的元素个数为{}", count);

        /**
         * 一个Stream表面上与一个集合很类似，允许你改变和获取数据，但实际上却有很大区别：

         Stream自己不会存储元素。元素可能被存储在底层的集合中，或者根据需要产生出来。
         Stream操作符不会改变源对象。相反，他们返回一个持有新结果的Stream。
         Stream操作符可能是延迟执行的。意思是它们会等到需要结果的时候才执行。

         * Stream.of(strs).filter(s -> s.length() > 3).count();
         */
        final Stream<String> streamStrs = Stream.of(strs);
        final long count1 = streamStrs.filter(s -> s.length() > 3).count();
        log.info("java8 lambda方式字符串长度大于3的元素个数为{}", count1);


        /**
         * 以下代码为了展示每个操作的返回值
         * 实际可以连写 Arrays.asList(strs).parallelStream().filter(s -> s.length() > 3).count();
         */
        final List<String> strings = Arrays.asList(strs);
        final Stream<String> stringStream = strings.parallelStream();
        final long c = stringStream.filter(s -> s.length() > 3).count();
        log.info("java8 lambda方式字符串长度大于3的元素个数为{}", c);


        final IntStream intStream = IntStream.of(new int[]{1, 2, 3, 4});
        intStream.forEach(e -> log.info("element is {}", e));

        /**
         * 左闭右开[a,b)
         */
        final IntStream range = IntStream.range(1, 5);
        range.forEach(e -> log.info("range element is {}", e));

        /**
         * 左闭右闭[a,b]
         */
        final IntStream intStream1 = IntStream.rangeClosed(1, 5);
        intStream1.forEach(e -> log.info("rangeClosed element is {}", e));


    }

    /**
     * Stream转换为其他数据结构
     */
    @Test
    public void testTransfer() {
        Stream<String> stringStream = Stream.of(strs);


        /**
         * 转换为List
         * Collectors.toList()
         *
         * 指定具体的实现类
         * Collectors.toCollection(ArrayList::new)
         */
        final List<String> collect = stringStream.collect(Collectors.toList());

        Stream<String> stream2 = Stream.of(strs);
        final ArrayList<String> collect1 = stream2.collect(Collectors.toCollection(ArrayList::new));

        Stream<String> stream3 = Stream.of(strs);
        final HashSet<String> collect2 = stream3.collect(Collectors.toCollection(HashSet::new));

        final Stream<String> stream5 = Stream.of(strs);
        final String collect3 = stream5.collect(Collectors.joining("#"));
        log.info("collect3 ={}", collect3);

        final Stream<String> stream6 = Stream.of(strs);
        final IntSummaryStatistics summaryStatistics = stream6.collect(Collectors.summarizingInt(String::length));
        final long count = summaryStatistics.getCount();
        final long sum1 = summaryStatistics.getSum();
        log.info("count ={},sum1={}", count, sum1);

        /**
         * java.lang.IllegalStateException: stream has already been operated upon or closed
         一个流只能被使用一次
         */
//        List<String> list2 = stringStream.collect(Collectors.toCollection(ArrayList::new));
//
//        final Set<String> collect1 = stringStream.collect(Collectors.toSet());
//        final HashSet<String> collect2 = stringStream.collect(Collectors.toCollection(HashSet::new));
//
//        final String[] strings = stringStream.toArray(String[]::new);
        final User user = User.generateUser();
        final User user1 = User.generateUser();
        final User user2 = User.generateUser();
        final User user3 = User.generateUser();
        final User user4 = User.generateUser();
        final User user5 = User.generateUser();
        final User user6 = User.generateUser();

        List<User> userList = Lists.newArrayList(user, user1, user2, user3, user4, user5, user6);

        int s = 0;
        for (User u : userList) {
            log.info("user age= {}", u.getAge());
            s = s + u.getAge();
        }
        log.info("年龄求和={}", s);

        /**
         * map,把输入流的每一个元素，映射为输出流的每一个元素
         * 一对一
         */
        final int sum = userList.parallelStream().mapToInt(User::getAge).sum();
        log.info("lambda方式年龄求和={}", sum);

        final List<Long> idList = userList.parallelStream().map(User::getId).collect(Collectors.toList());
        log.info("获取ID集合={}", idList);
        userList.parallelStream().filter(u -> u.getAge() > 20).map(User::getId).collect(Collectors.toList());

        /**
         * 根据userType分组
         */
        final Map<Integer, List<User>> collect4 = userList.stream().collect(Collectors.groupingBy(User::getUserType));
        log.info("根据userType分组 collect4={}", collect4);


        /**
         * 当分类函数返回的是一个Boolean类型返回值的函数，流元素会被分为两组，一组是TRUE的，一组是FALSE的，
         * 这时候，使用partitioningBy会比groupingBy更有效率
         * 实际用途：查一次库，获取所有和该用户相关的数据，isActive=1的分一组，isActive=0的分一组
         */
        final Map<Boolean, List<User>> collect5 =
                userList.stream().collect(Collectors.partitioningBy(a -> a.getUserType().intValue() == 1));
        log.info("根据userType分组 是1的一组，非1的一组 collect5={}", collect5);
        /**
         * true 即符合你上述条件的
         */
        final List<User> users = collect5.get(true);


        /**
         * 方法引用
         * 类::静态方法
         * 类::实例方法
         * 对象::实例方法
         *
         * Math::power   等同于 (x,y)->Math.power(x,y)
         */
        //final List<Long> idList3 = userList.parallelStream().map(User::getId).collect(Collectors.toList());
        final List<Long> idList2 = userList.parallelStream().map(u -> u.getId()).collect(Collectors.toList());


        final Map<Integer, Optional<User>> collect6 = userList.stream().collect(Collectors.groupingBy(User::getUserType, Collectors.maxBy(Comparator.comparing(User::getId))));

        /**
         * 转Map
         */
        final Map<Long, Integer> map1 = userList.stream().collect(Collectors.toMap(User::getId, User::getAge));
        log.info("map1={}", map1);

        final Map<Long, User> map2 = userList.stream().collect(Collectors.toMap(u1 -> u1.getId(), u2 -> u2));
        log.info("map2={}", map2);

        final String s1 = Optional.ofNullable(user4.getUserName()).orElse("鉴权失败");
        log.info("s ={}", s1);

    }
}
