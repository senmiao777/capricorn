package com.frank.designpattern.adapter;

/**
 * @author: sb
 * @time: 2018-07-19 15:31
 * 适配器类，核心
 * 将一个类的接口，转换为客户端锁需要的另一种接口，从而使原本因为接口不匹配而无法一起工作的两个类能一起工作
 * 适配器，给源对象，也就是待适配对象套了个壳，一个目标对象的壳.
 * <p>
 * 优点：
 * 可以让两个原本没关系的类一起工作
 * 灵活性高，不想用某个适配器了删了就行了，不会影响源对象和目标对象
 * <p>
 * 使用场景：
 * 需要修改一个已经在生产中使用的接口时
 * <p>
 * 生产中经典的例子就是对枚举遍历的适配
 * Vector vector = new Vector();
 * Enumeration elements = vector.elements();
 * EnumerationIterator enumerationIterator=new EnumerationIterator(elements);
 * <p>
 * EnumerationIterator作为一个适配器，使Enumeration具有Iterator的遍历方法
 */

public class Adapter implements Target {

    /**
     * 为什么这里是持有待适配对象的引用，而不是直接继承该对象呢？
     * 试想一下，如果你这个适配器，要适配多个类，你用继承是行不通的。
     */
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void targetOperation() {
        /**
         * 这里进行适配工作
         * 将Adaptee适配成Target的子类Adapter
         */
        adaptee.adapteeMethod1();
    }
}
