package com.frank.designpattern;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * <p>
 * 被观察者-恶魔
 */
@Slf4j
public class Demon implements Observable {

    List<Observer> observers = new ArrayList<>();

    /**
     * 添加观察者
     *
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }


    /**
     * 删除观察者
     *
     * @param observer
     */
    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }


    /**
     * 通知观察者
     *
     * @param msg
     */
    @Override
    public void notifyObservers(Notice msg) {
        for (Observer o : observers) {
            o.action(msg);
        }
    }

    @Override
    public void eat(String food) {
        log.info("Demon eat {}", food);
        notifyObservers(new Notice(OperationType.EAT, getClass().toString(), food));
    }

    @Override
    public void sleep(String time) {
        log.info("Demon sleep {}", time);
        notifyObservers(new Notice(OperationType.SLEEP, getClass().toString(), time));
    }


}