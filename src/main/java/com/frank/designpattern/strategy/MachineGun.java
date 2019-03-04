package com.frank.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:47
 */
@Slf4j
public class MachineGun implements Fightable {
    @Override
    public void fight() {
        log.info("用机关枪战斗");
    }
}
