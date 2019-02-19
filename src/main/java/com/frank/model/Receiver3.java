package com.frank.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author frank
 * @version 1.0
 * @date 2019/2/19 0019 下午 10:00
 */
@Component
@Slf4j
public class Receiver3 {

    @RabbitListener(queues = "testQueue")
    public void receiver0(String str) {
        log.info("Receiver3 testQueue receiver000收到={}",str);
    }

    @RabbitListener(queues = "testQueue")
    public void receiver2(String str) {
        log.info("Receiver3 testQueue receiver222收到={}",str);
    }

//    @RabbitListener(queues = "secondQueue")
//    public void receiver1(String str) {
//        log.info("Receiver3 secondQueue receiver111收到={}",str);
//    }
}
