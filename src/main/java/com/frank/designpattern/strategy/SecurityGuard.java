package com.frank.designpattern.strategy;

/**
 * @author: somebody
 * @time: 2019-03-04 12:02
 * 保安具有战斗特性
 */
public class SecurityGuard extends Person {


    public SecurityGuard() {
        fightable = new Pistol();
    }

}
