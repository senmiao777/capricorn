package com.frank.designpattern.adapter;

        import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-07-19 15:25
 * 待处理对象，即将该对象适配成Target对象（目标接口）
 * 比如说这就是生产中已经存在的对象，你不能修改他的代码
 * 可以通过给他加个壳，解决和现有客户端不兼容的问题
 */
@Slf4j
public class Adaptee {

    public void adapteeMethod1() {
        log.info("Adaptee adapteeMethod1...");
    }
}
