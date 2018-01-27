package com.frank.proxy.impl;

import com.frank.proxy.Subject2;
import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 4:57
 */
@Slf4j
public class RealSubject implements Subject2{
    public RealSubject() {}

    @Override
    public  void call() {
        log.info("[RealSubject] From real subject---");
    }

    @Override
    public String call(String msg) {
        log.info("[RealSubject]msg={}",msg);
        return msg;
    }
}
