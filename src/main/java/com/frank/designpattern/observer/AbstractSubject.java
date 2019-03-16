package com.frank.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/16 0016 下午 7:25
 */
public abstract class AbstractSubject implements Subject {

    List<Observer> observers = new ArrayList<>();

    @Override
    public void notifyObservers(Notice msg) {
        for (Observer o : observers) {
            o.action(msg);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        if (observers.indexOf(observer) > 0) {
            observers.remove(observer);
        }
    }
}
