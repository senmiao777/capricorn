package com.frank.designpattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * CIA
 */
@Slf4j
public class CIA implements Observer {
    @Override
    public void action(Observable.Notice notice) {
        log.info("CIA监听到{} {}活动，内容{}", notice.getObservable(), notice.getOperationType(), notice.getObj());
    }
}
