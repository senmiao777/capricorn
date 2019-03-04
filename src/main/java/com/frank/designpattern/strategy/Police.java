package com.frank.designpattern.strategy;

import com.frank.designpattern.template.Hummer;

/**
 * @author: somebody
 * @time: 2019-03-04 12:02
 * 警察具有战斗特性
 */
public class Police extends Person {

    /**
     * 要用哪个策略，就用具体的策略实现类给父类对应的策略接口实例进行赋值
     */
    public Police() {
        driveable = new Hummer();
        fightable = new Macaque();
    }
}
