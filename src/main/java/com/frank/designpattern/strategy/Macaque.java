package com.frank.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:47
 * [məˈkæk] 狙;猢狲;恒河猴，猕猴，叟猴
 */
@Slf4j
public class Macaque implements Fightable {
    @Override
    public void fight(String name) {
        log.info("{} 用狙战斗", name);
    }
}
