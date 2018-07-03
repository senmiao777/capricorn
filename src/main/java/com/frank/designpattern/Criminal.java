package com.frank.designpattern;

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
     * @param     msg

     */
    @Override
    public void notifyObservers(String msg) {
        for (Observer o:observers) {
            o.action(msg);
        }
    }

    public void eat(String food){
        log.info("Criminal eat {}",food);
      notifyObservers(food);
    }


}
