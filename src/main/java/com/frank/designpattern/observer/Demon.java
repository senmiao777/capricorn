package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * <p>
 * 被观察者-恶魔
 */
@Slf4j
public class Demon extends Observable {

    public void eat(String food) {
        log.info("Demon eat {}", food);
        super.setChanged();
        super.notifyObservers(new Subject.Notice(Subject.OperationType.EAT, this.getClass().getSimpleName(),food));
    }

}
