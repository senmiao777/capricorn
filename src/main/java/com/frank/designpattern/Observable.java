package com.frank.designpattern;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 被观察者
 */
public interface Observable {
    /**
     * 添加观察者
     *
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 删除观察者
     *
     * @param observer
     */
    void deleteObserver(Observer observer);

    /**
     * 通知观察者
     *
     * @param msg
     */
    void notifyObservers(String msg);


    enum OperationType{
        EAT,

    }
}
