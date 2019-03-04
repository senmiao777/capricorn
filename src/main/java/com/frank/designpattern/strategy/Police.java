package com.frank.designpattern.strategy;

/**
 * @author: somebody
 * @time: 2019-03-04 12:02
 * 警察具有战斗特性
 */
public class Police extends AbstractPerson implements Fightable {


    @Override
    public void fight() {
        fightable = new Pistol();
        super.fight();
    }
}
