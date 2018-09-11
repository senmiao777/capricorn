package com.frank.designpattern.decorator;

/**
 * Created by sb on 2018/7/18.
 * 如果只有一个装饰器类，则不需要写个抽象类了，直接继承就行了
 * 装饰器优点：
 * 解耦，各个装饰器实现类之间没有任何耦合，装饰器和组件类也没有耦合关系，可任意添加删除。
 * 扩展性强，装饰器可实现对组件类的动态扩展。
 * <p>
 * 装饰类越多越复杂，排错困难
 * 所以说，可以的话，要做的事在一个装饰器里做了就OK
 * <p>
 * 使用场景：
 * 就一句话，当需要动态扩展类的功能的时候
 */
public abstract class Decorator implements Component {

    /**
     * 把需要装饰的组件通过装饰器的构造函数传进来
     */
    Component component = null;

    public Decorator(Component component) {
        this.component = component;
    }


    @Override
    public void operation() {
        /**
         * 最基本的功能是核心组件提供
         */
        this.component.operation();
    }
}
