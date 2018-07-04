package com.frank.designpattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.Observer;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/4
 * CIA
 */
@Slf4j
public class CIA implements Observer {

    @Async("testTaskPoolExecutor")
    @Override
    public void update(java.util.Observable o, Object notice) {
        log.info("CIA监听到{} 内容{}", o.toString(), notice.toString());
    }

}
