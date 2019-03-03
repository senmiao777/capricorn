package com.frank.designpattern.strategy;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:40
 * 策略调用者的接口
 */
public interface Person {

    /**
     * 战斗方法,并不是所有的人都有该方法
     */
    void fight();

    /**
     * 吃方法，所有的人都有
     */
    void eat();
}
