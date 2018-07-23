package com.frank.designpattern.adapter;

/**
 * @author: sb
 * @time: 2018-07-19 15:31
 * 适配器类，核心
 */
public class Adapter extends Adaptee implements Target {

    /**
     * 将一个类的接口，转换为客户端锁需要的另一种接口，从而使原本因为接口不匹配而无法一起工作的两个类能一起工作
     * 适配器，给源对象，也就是待适配对象套了个壳
     * 一个目标对象的壳
     * 你感觉是在访问目标对象，其实里边是调用的源对象的方法
     * <p>
     * <p>
     * 优点：
     * 可以让两个原本没关系的类一起工作
     * 灵活性高，不想用某个适配器了删了就行了，不会影响源对象和目标对象
     * <p>
     * 使用场景：
     * 需要修改一个已经在生产中使用的接口时
     */
    @Override
    public void targetOperation() {
        super.adapteeMethod1();
    }
}