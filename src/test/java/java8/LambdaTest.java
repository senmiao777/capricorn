package java8;

import com.frank.entity.java8.Apple;
import com.frank.enums.Color;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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


    /**
     * 链表反转
     */
    @Test
    public void testLambda1() {
        Apple apple1 = new Apple(Color.RED.getColor(), 1f);
        Apple apple2 = new Apple(Color.RED.getColor(), 2f);
        Apple apple3 = new Apple(Color.GREEN.getColor(), 3f);


    }

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

}
