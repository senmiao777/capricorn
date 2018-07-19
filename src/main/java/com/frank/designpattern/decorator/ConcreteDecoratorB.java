package com.frank.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-07-18 15:40
 */
@Slf4j
public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(Component component) {
        super(component);
    }


    @Override
    public void operation() {
        decoratorMethod1();
        super.operation();
    }

    /**
     * 装饰方法
     */
    private void decoratorMethod1() {
        log.info("ConcreteDecoratorB decoratorMethod1 ...");
    }

}
