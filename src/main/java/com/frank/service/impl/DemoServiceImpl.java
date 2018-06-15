package com.frank.service.impl;

import com.frank.service.DemoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 4:57
 */
@Slf4j
public class DemoServiceImpl implements DemoService {
    public DemoServiceImpl() {
    }

    @Override
    public void call() {
        log.info("[DemoServiceImpl]From real service");
    }

    @Override
    public String call(String msg) {
        log.info("[DemoServiceImpl]From real service msg={}", msg);
        return msg.concat("call method call(String msg)");
    }
}
