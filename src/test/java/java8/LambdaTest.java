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


/**
 * @author frank
 * @version 1.0
 * @date 2022年1月1日10:22:01
 */

@Rollback(false)
@Slf4j
public class LambdaTest {

    final String BEI_JING = "beijing";
    final String QING_DAO = "qing_adao";

    /**
     * 为什么要用lambda表达式
     */
    @Test
    public void testLambda1() {
        Apple apple1 = new Apple(Color.RED.getColor(), 1f, BEI_JING);
        Apple apple2 = new Apple(Color.RED.getColor(), 2f, BEI_JING);
        Apple apple3 = new Apple(Color.GREEN.getColor(), 3f, QING_DAO);


    }

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
