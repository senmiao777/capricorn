package com.frank.designpattern.decorator;

/**
 * Created by sb on 2018/7/18.
 * 如果只有一个装饰器类，则不需要写个抽象接口了，直接继承就行了
 */
public abstract class Decorator implements Component {

    Component component = null;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        this.component.operation();
    }
}
