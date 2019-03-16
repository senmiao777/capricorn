package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.Observable;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * 警察类
 * Spring提供的观察者模式
 */
@Slf4j
public class Soldier implements java.util.Observer {
    public Soldier() {
    }

    @Async("testTaskPoolExecutor")
    @Override
    public void update(Observable o, Object arg) {
        try {
            log.info("Soldier监听到{} 内容{}", o.getClass().getSimpleName(), arg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
