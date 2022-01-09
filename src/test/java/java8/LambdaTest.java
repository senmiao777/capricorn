package java8;

import com.frank.entity.java8.Apple;
import com.frank.enums.Color;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;


/**
 * @author frank
 * @version 1.0
 * @date 2022年1月1日10:22:01
 */

@Rollback(false)
@Slf4j
public class LambdaTest {

    final String BEI_JING = "beijing";
    final String QING_DAO = "qing_dao";

    /**
     * 为什么要用lambda表达式
     */
    @Test
    public void testLambda1() {
        Apple apple1 = new Apple(Color.RED.getColor(), 10f, BEI_JING);
        Apple apple2 = new Apple(Color.RED.getColor(), 20f, BEI_JING);
        Apple apple3 = new Apple(Color.GREEN.getColor(), 30f, QING_DAO);

        List<Apple> appleList = new ArrayList<>(8);
        appleList.add(apple1);
        appleList.add(apple2);
        appleList.add(apple3);

        /**
         * 根据颜色筛选苹果
         */
        final List<Apple> applesByColor = getApplesByColor(appleList, Color.RED.getColor());

        final List<Apple> collect = appleList.stream().filter(apple -> Color.GREEN.getColor().equals(apple.getColor()) &&
                apple.getWeight() > 10.0f &&
                QING_DAO.equals(apple.getArea())).collect(Collectors.toList());


        if (CollectionUtils.isNotEmpty(collect)) {
            collect.forEach(System.out::println);
        } else {
            System.out.println("no match");
        }

        System.out.println("Function复合函数：" + a.andThen(b).apply(1));
        System.out.println("BiFunction复合函数：" + c.andThen(a).apply(1,3));

    }

    /**
     * 像这种Int ,Float之类开头的函数式接口，是为了支持基本类型，避免不必要的拆箱装箱，性能高
     * 装箱，本质是将基本类型包了个壳，然后创建一个对象类型的对象，保存在堆里。
     * <p>
     * UnaryOperator，一元操作，输入类型和返回类型一致，其实就是Function的特例。
     * <p>
     * a.andThen(b) 是先执行函数a，在执行函数b
     * <p>
     * a.compose(b) 是先执行函数b，在执行函数a
     */
    private Function<Integer, Integer> a = (a) -> a + 1;

    private Function<Integer, Integer> b = (a) -> a * 2;

    private BiFunction<Integer, Integer,Integer> c = Integer::sum;


    /**
     * 筛选红苹果
     *
     * @param apples
     * @return
     */
    private List<Apple> getRedApples(List<Apple> apples) {
        List<Apple> appleList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(apples)) {
            for (Apple a : apples) {
                if (Color.RED.getColor().equals(a.getColor())) {
                    appleList.add(a);
                }
            }
        }
        return appleList;
    }

    /**
     * 按颜色筛选苹果
     *
     * @param apples
     * @return
     */
    private List<Apple> getApplesByColor(List<Apple> apples, String color) {
        List<Apple> appleList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(apples) && StringUtils.isNotBlank(color)) {
            for (Apple a : apples) {
                if (color.equals(a.getColor())) {
                    appleList.add(a);
                }
            }
        }
        return appleList;
    }

    /**
     * 按重量筛选苹果
     * 如果要按产地筛选呢，那就需要再复制一遍代码，改一下判断逻辑，在写一个按产地筛选的方法
     * 如果要按重量和颜色筛选呢，那就需要再再复制一遍代码，改一下判断逻辑。。。
     * 如果要按重量和产地筛选呢，那就需要再再复制一遍代码，改一下判断逻辑。。。
     * 如果要按重量和产地和颜色筛选呢，那就需要再再再复制一遍代码，改一下判断逻辑。。。
     * <p>
     * 违背了 “don't repeat yourself” 原则，即不要写重复代码
     * <p>
     * 那么，lambda的好处就提现出来了，直接“传递行为”，把要干什么，作为参数，传递到方法中。
     * 结合匿名类，实现声明的同时实例化一个类，达到“随建随用”的效果
     *
     * @param apples
     * @return
     */
    private List<Apple> getApplesByColor(List<Apple> apples, float weight) {
        List<Apple> appleList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(apples)) {
            for (Apple a : apples) {
                if (a.getWeight() > weight) {
                    appleList.add(a);
                }
            }
        }
        return appleList;
    }

}
