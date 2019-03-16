package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 被观察者-罪犯
 */
@Slf4j
public class Criminal extends AbstractSubject {

    private int age;
    private String name;

    public void sleep(String time) {
        log.info("Criminal sleep {}", time);
        /**
         * 被观察者要等待观察者处理完成之后才能继续往下走，考虑性能问题
         */
        notifyObservers(new Notice(OperationType.SLEEP,this.getClass().getSimpleName(), time));
        log.info("do next things");
    }

    public int getAge() {
        return age;
    }

    /**
     * 当年龄发生修改了，通知注册的观察者
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
        notifyObservers(new Notice(OperationType.UPDATE_AGE,this.getClass().getSimpleName(),age));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
