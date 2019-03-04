package com.frank.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:44
 * <p>
 * 调用者的抽象类
 * 抽象类持有各种策略的实例（接口），比如飞行策略，游泳策略，计算策略，下载策略等等。
 * 在子类进行具体策略实例的初始化，这块是硬编码的，在子类知道具体用哪类策略的哪个实例。
 * 比如用飞行策略（fly）的火箭飞行实例（rocket）。
 * 子类直接或间接的调用fly方法，不需要知道rocket的具体实现逻辑，rocket的实现变了也不会影响，因为子类持有的接口类型的引用。
 *
 * 具体的策略实例可以被复用，在需要用到该策略的抽象类里加入策略接口的一个实例即可。
 */
@Slf4j
@Component
public abstract class Person {

    Fightable fightable;

    Driveable driveable;

    public void eat() {
        log.info("I can eat...");
    }

    public void drive(){
        driveable.drive(this.getClass().getSimpleName());
    }

    public void fight() {
        fightable.fight(this.getClass().getSimpleName());
    }

}
