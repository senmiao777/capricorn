package com.frank.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-07-18 15:12
 */
@Slf4j
public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        log.info("ConcreteComponent operation");
    }
}
