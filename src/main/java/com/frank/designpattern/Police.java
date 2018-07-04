package com.frank.designpattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * 警察类
 */
@Slf4j
public class Police implements Observer {

    @Async("testTaskPoolExecutor")
    @Override
    public void action(Observable.Notice notice) {
        try {
            // 模拟延迟
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Police监听到{} {}活动，内容{}", notice.getObservable(), notice.getOperationType(), notice.getObj());

    }
}
