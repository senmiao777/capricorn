package com.frank.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author frank
 * @version 1.0
 * @date 2018/9/2 0002 下午 8:39
 *
 */
@Component
@Slf4j
@RabbitListener(queues = "testQueue")
public class Receiver {

    @RabbitHandler
    public void receiveMessage(String message) {
        log.info("testQueue receiveMessage called.message={}", message);
    }
}
