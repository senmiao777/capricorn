package com.frank.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-07-19 15:22
 * 具体的目标对象
 */
@Slf4j
public class ConcreteTarget implements Target {
    @Override
    public void targetOperation() {
        log.info("ConcreteTarget targetOperation");
    }
}
