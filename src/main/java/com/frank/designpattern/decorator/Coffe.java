package com.frank.designpattern.decorator;

import java.math.BigDecimal;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/17 0017 上午 10:52
 * 饮料的具体子类，咖啡类
 */
public class Coffe implements Beverage {

    private static final BigDecimal PRICE = new BigDecimal("15");

    @Override
    public BigDecimal cost() {
        return PRICE;
    }
}
