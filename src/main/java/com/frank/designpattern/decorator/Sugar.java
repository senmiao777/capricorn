package com.frank.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/17 0017 上午 11:35
 */
@Slf4j
public class Sugar extends CondimentDecorator{
    private final BigDecimal SUGAR_PRICE = new BigDecimal("1.20");

    /**
     * 持有饮料的引用
     */
    private Beverage beverage;

    public Sugar (Beverage beverage){
        this.beverage = beverage;
    }

    @Override
    public BigDecimal cost() {
        log.info("Sugar装饰器，添加了糖");
        return SUGAR_PRICE.add(beverage.cost());
    }
}
