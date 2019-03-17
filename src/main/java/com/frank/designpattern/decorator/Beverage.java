package com.frank.designpattern.decorator;

import java.math.BigDecimal;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/17 0017 上午 10:50
 *
 * 饮料接口，需要被包装的组件
 */
public interface Beverage {
    /**
     * 饮料的价格
     * @return
     */
    BigDecimal cost();
}
