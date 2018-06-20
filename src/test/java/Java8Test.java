import com.frank.entity.mysql.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;
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

        final List<String> collect = stringStream.collect(Collectors.toList());
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

        List<User> userList = Lists.newArrayList(user, user1, user2, user3, user4);

        int s = 0;
        for (User u : userList) {
            log.info("user age= {}", u.getAge());
            s = s + u.getAge();
        }
        log.info("年龄求和={}", s);

        /**
         * map,把输入流的每一个元素，映射为输出流的每一个元素
         */
        final int sum = userList.parallelStream().mapToInt(User::getAge).sum();
        log.info("lambda方式年龄求和={}", sum);

        final List<Long> idList = userList.parallelStream().map(User::getId).collect(Collectors.toList());
        log.info("获取ID集合={}", idList);


    }
}
