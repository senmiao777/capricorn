package com.frank.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-07-19 15:25
 * 待处理对象，即将该对象适配成Target对象
 */
@Slf4j
public class Adaptee {

    public void adapteeMethod1(){
        log.info("Adaptee adapteeMethod1...");
    }
}
