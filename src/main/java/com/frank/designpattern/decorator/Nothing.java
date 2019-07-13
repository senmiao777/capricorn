package com.frank.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/17 0017 上午 11:35
 * 具体的装饰器，奶类
 * 具体的装饰器持有被装饰者（组件）的引用
 */
@Slf4j
@Component
public class Nothing extends CondimentDecorator{

    private final BigDecimal MILK_PRICE = new BigDecimal("2.30");

    public Nothing(){

    }

    @Override
    public BigDecimal cost() {
        log.info("Nothing装饰器，添加了Nothing");
        return MILK_PRICE.add(MILK_PRICE);
    }
}
