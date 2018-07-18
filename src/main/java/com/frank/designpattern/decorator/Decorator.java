package com.frank.designpattern.decorator;

/**
 * Created by sb on 2018/7/18.
 */
public abstract class Decorator implements Component {

    Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        this.component.operation();
    }
}
