package com.frank.designpattern.strategy;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:45
 * 开车策略接口，实现该接口的类就会具有开车特性
 */
public interface Driveable {
    void drive(String name);
}
