package com.frank.designpattern.template;

import com.frank.designpattern.strategy.Driveable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/2 0002 下午 10:00
 * 福特汽车，继承汽车类
 */
@Slf4j
public class Ford extends MotorVehicle implements Driveable {
    /**
     * 启动方法
     */
    @Override
    protected void start(int second) {
        log.info("Ford start cost {} second",second);
    }

    /**
     * 鸣笛方法
     */
    @Override
    protected void alarm(){
        log.info("Ford alarm");
    }

    /**
     * 行驶方法
     */
    @Override
    protected void move(){
        log.info("Ford move");
    }

    /**
     * 停车方法
     */
    @Override
    protected void stop(){
        log.info("Ford stop");
    }

    @Override
    public void drive(String name) {
        log.info("{} 开福特上班",name);
    }
}
