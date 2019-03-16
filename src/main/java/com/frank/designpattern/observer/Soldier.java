package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * 警察类
 */
@Slf4j
public class Soldier implements Observer {
    private Subject subject;

    public Soldier(Subject subject) {
        this.subject = subject;
        subject.addObserver(this);
    }


    @Async("testTaskPoolExecutor")
    @Override
    public void action(Subject.Notice notice) {
        try {
            // 模拟延迟
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Soldier监听到{} {}活动，内容{}", notice.getSubject(), notice.getOperationType(), notice.getObj());

    }
}
