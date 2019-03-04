package com.frank.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:44
 */
@Slf4j
@Component
public abstract class Person {

    Fightable fightable;

    public void eat() {
        log.info("I can eat...");
    }

    public void fight() {
        fightable.fight(this.getClass().getSimpleName());
    }

}
