package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 被观察者-罪犯
 */
@Slf4j
public class Criminal implements Observable {

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
        log.info("Criminal eat {}", food);
        notifyObservers(new Notice(OperationType.EAT, getClass().toString(), food));
    }

    @Override
    public void sleep(String time) {
        log.info("Criminal sleep {}", time);
        /**
         * 被观察者要等待观察者处理完成之后才能继续往下走，考虑性能问题
         */
        notifyObservers(new Notice(OperationType.SLEEP, getClass().toString(), time));
        log.info("do next things");
    }


}
