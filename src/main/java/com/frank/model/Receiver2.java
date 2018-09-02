package com.frank.model;

import com.frank.annotation.MDCLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author frank
 * @version 1.0
 * @date 2018/9/2 0002 下午 8:39
 */
@Component
@Slf4j
@RabbitListener(queues = "secondQueue")
public class Receiver2 {

    @MDCLog
    @RabbitHandler
    public void receiveMethod(String message) {
        log.info("secondQueue receiveMethod called.message={}", message);
    }
}
