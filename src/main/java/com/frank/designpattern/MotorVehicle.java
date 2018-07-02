package com.frank.designpattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/2 0002 下午 9:53
 * <p>
 * 汽车抽象类，包含最基本的方法定义
 */
@Slf4j
public abstract class MotorVehicle {

    /**
     * 是否鸣笛标志位
     * 默认不鸣笛
     */
    protected boolean isAlarm = false;

    /**
     * 启动方法
     */
    protected void start() {
        log.info("MotorVehicle start");
    }

    ;

    /**
     * 鸣笛方法
     */
    protected void alarm() {
        log.info("MotorVehicle alarm");
    }

    /**
     * 行驶方法
     */
    protected void move() {
        log.info("MotorVehicle move");
    }

    /**
     * 停车方法
     */
    protected void stop() {
        log.info("MotorVehicle stop");
    }

    /**
     * 模板方法
     */
    public void run() {
        start();
        boolean alarm = isAlarm();
        if (alarm) {
            alarm();
        }
        move();
        stop();
    }

    protected boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }
}
