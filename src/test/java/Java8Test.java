import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * @author frank
 * @version 1.0
 * @date 2018/6/20
 */
@Slf4j
public class Java8Test {

    public String[] strs = {"new", "Java8", "new", "feature", "Stream", "API"};

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


    }
}
