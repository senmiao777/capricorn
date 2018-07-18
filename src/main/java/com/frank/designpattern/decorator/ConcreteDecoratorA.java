package com.frank.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-07-18 15:40
 */
@Slf4j
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }


    @Override
    public void operation() {
        decoratorMethod1();
        //this.component.operation();
        super.operation();
    }

    /**
     * 装饰方法
     */
    private void decoratorMethod1() {
        log.info("ConcreteDecoratorA decoratorMethod1 ...");
    }

}
