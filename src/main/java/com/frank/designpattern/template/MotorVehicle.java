package com.frank.designpattern.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/2 0002 下午 9:53
 * <p>
 * 汽车抽象类，包含最基本的方法定义
 * <p>
 * 模板方法模式。
 * 父类定义好框架结构，要干什么事，子类实现具体步骤。
 * 比如有个模板方法叫开车，开车方法里有四个步骤，启动，鸣笛，运行，停车。这四个步骤是一定的，子类不能改，子类可以改的是方法实现。
 * 比如福特的鸣笛声音是呜呜，悍马的鸣笛声音是轰隆。
 * <p>
 * 基本方法：在模板中调用的方法，比如例子中的启动、鸣笛、运行、停车
 * 模板方法：组合调用基本方法的方法，完成固定的逻辑，比如例子中的开车方法。
 * 为了避免被覆盖，模板方法都是final修饰的。
 * <p>
 * 有点：代码重用性好
 * 代码便于维护，只要改一个地方就可以
 * 方便扩展，子类可以有自己的实现
 * <p>
 * 钩子方法：
 * 由子类的方法决定父类方法的逻辑
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
     * 启动耗时
     */
    protected abstract void start(int second);

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
    public final void run(int second) {
        start(second);
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
