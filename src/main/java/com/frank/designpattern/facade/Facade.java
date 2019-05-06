package com.frank.designpattern.facade;

import com.frank.designpattern.command.Airconditioner;
import com.frank.designpattern.command.Light;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-09-20 18:43
 * 外观（门面）模式
 * 提供一个统一的接口，用来访问子系统中的一群接口。
 * 核心：简化客户端调用，外观定义了一个高层接口，让子系统更加容易使用。
 * <p>
 * 如果把一堆“接口”类比成一堆“命令”吗，那么外观模式很像是一个宏命令。
 * 也有点像模板模式，这个模板是各个子接口组合起来的。
 * <p>
 * 和适配器模式区别：
 * 侧重点不一样，适配器模式的核心是通过适配让不满足用户需求的接口（类）变成符合用户需求的接口。而外观模式是为了简化调用
 * 优点：
 * 1、灵活性好，屏蔽子系统，对外部系统透明，子系统变了不影响调用方。
 * 2、安全性高，方便进行访问控制，如果需要做控制的话。
 * 缺点：
 * 完全和子系统绑死，太依赖外观对象。需要一组操作就需要一个新的外观。
 * <p>
 * <p>
 * 实际例子：
 * spring ApplicationContext;
 * 它实现了Factory、ResourceLoader等接口，并通过引用这些接口的实例，对外统一提供：加载配置、解析资源、创建Bean、提供环境、启动流程等功能；
 * 客户代码只需要操作context就可以获取spring的提供的功能，而无需关心内部的细节。
 */
@Slf4j
public class Facade {

    /**
     * 持有子系统对象的引用
     * 设计原则：多用组合，少用继承
     */
    Light light;
    Airconditioner airconditioner;

    public Facade(Light light, Airconditioner airconditioner) {
        this.light = light;
        this.airconditioner = airconditioner;
    }

    /**
     * 外观方法
     * 客户端只需要调用这一个方法。该方法内部会调用一堆子系统来完成具体的调用
     */
    public void open() {
        this.light.on();
        this.airconditioner.on();
        /**
         * and so on
         */
    }

}
