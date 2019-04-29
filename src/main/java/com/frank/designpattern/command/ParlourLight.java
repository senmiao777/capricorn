package com.frank.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: somebody
 * @time: 2019-04-29 11:44
 */
@Slf4j
public class ParlourLight implements Light {

    @Override
    public void on() {
        log.info("ParlourLight开启");
    }

    @Override
    public void off() {
        log.info("ParlourLight关闭");
    }
}
