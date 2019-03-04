package com.frank.designpattern.strategy;

/**
 * @author: somebody
 * @time: 2019-03-04 12:02
 * 士兵具有战斗特性
 */
public class Soldier extends AbstractPerson implements Fightable {


    @Override
    public void fight() {
        /**
         * 士兵现在是用的是机枪
         */
        fightable = new MachineGun();
        super.fight();
    }
}
