package com.frank.designpattern.observer;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 观察者
 */
public interface Observer {
    void action(Observable.Notice notice);
}
