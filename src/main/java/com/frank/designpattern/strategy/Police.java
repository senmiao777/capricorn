package com.frank.designpattern.strategy;

import com.frank.designpattern.template.Hummer;

/**
 * @author: somebody
 * @time: 2019-03-04 12:02
 * 警察具有战斗特性
 */
public class Police extends Person {

    public Police() {
        driveable = new Hummer();
        fightable = new Macaque();
    }
}
