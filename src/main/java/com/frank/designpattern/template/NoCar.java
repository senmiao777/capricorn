package com.frank.designpattern.template;

import com.frank.designpattern.strategy.Driveable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: somebody
 * @time: 2019-03-04 16:30
 */
@Slf4j
public class NoCar extends MotorVehicle implements Driveable {
    @Override
    public void drive(String name) {
        log.info("{} 是个屌丝，没有车可以开", name);
    }

    @Override
    protected void start(int second) {
        log.info("屌丝没有车");
    }
}
