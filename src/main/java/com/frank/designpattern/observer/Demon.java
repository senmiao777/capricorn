package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * <p>
 * 被观察者-恶魔
 * Spring提供的观察者模式
 */
@Slf4j
public class Demon extends Observable {

    public void eat(String food) {
        log.info("Demon eat {}", food);
        /**
         * 调用了setChanged主题（被观察者）的发布才能生效
         * 这样是为了在某种条件下触发发布。比如当吃的food是鱼的时候才setChanged发布主题，吃别的不setChanged不发布。
         */
        super.setChanged();
        /**
         * 将引用传过去了，观察者可以通过这个引用用“拉”的方式获取主题的数据
         */
        super.notifyObservers(this);
    }

}
