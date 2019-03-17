package com.frank.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/17 0017 上午 11:35
 * 具体的装饰器，奶类
 * 具体的装饰器持有被装饰者（组件）的引用
 */
@Slf4j
public class Milk extends CondimentDecorator{

    private final BigDecimal MILK_PRICE = new BigDecimal("2.30");

    /**
     * 持有饮料的引用
     */
    private Beverage beverage;

    public Milk (Beverage beverage){
        this.beverage = beverage;
    }

    @Override
    public BigDecimal cost() {
        log.info("Milk装饰器，添加了牛奶");
        return MILK_PRICE.add(beverage.cost());
    }
}
