package com.frank.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: somebody
 * @time: 2019-03-04 12:02
 * 士兵具有战斗特性
 */
@Slf4j
public class Soldier extends Person {

    public Soldier() {
        /**
         * 士兵现在是用的是机枪
         */
        fightable = new MachineGun();
    }

    @Override
    public void fight() {
        log.info("Soldier 实现类，扩展了父类的fight方法");
        super.fight();
    }

}
