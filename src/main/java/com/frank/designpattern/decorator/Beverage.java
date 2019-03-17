package com.frank.designpattern.decorator;

import java.math.BigDecimal;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/17 0017 上午 10:50
 * <p>
 * 饮料接口，需要被包装的组件
 * <p>
 * 试想一个场景，买饮料加不同的配料，最终得到饮料的价格
 * 我买了一杯咖啡，加糖、加奶、加冰，计算价格；
 * 我买了一杯茶，加冰、加碳酸、加糖双倍，计算价格；
 * 怎么得到饮料最终是多少钱呢？
 * <p>
 * java.io包用到很多装饰器，比如
 * 如BufferedInputStream、ByteArrayInputStream等都是具体的装饰器，装饰了InputStream
 */
public interface Beverage {
    /**
     * 饮料的价格
     *
     * @return
     */
    BigDecimal cost();
}
