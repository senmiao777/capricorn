package com.frank.model;

/**
 * @author frank
 * @version 1.0
 * @date 2021/7/12 0012 下午 10:05
 */
public class AbstractOrder {

    /**
     * static 方法，没有多态的第三条
     * 多态：
     * 1、对象类型不变
     * 2、只能对引用调用其引用类型中有的方法。即如果是引用是接口类型，则只能调用接口中定义的方法，不能调用子类对象中有而接口中没有的方法
     * 3、运行时，根据对象实际类型找子类对象覆盖之后的方法
     * （static方法是不能被覆盖的）
     */
    static void staticPrintOrder() {
        System.out.println("super staticPrintOrder");
    }

    void commonPrintOrder() {
        System.out.println("super commonPrintOrder");
    }
}
