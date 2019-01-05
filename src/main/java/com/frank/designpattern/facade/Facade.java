package com.frank.designpattern.facade;

import com.frank.designpattern.adapter.ConcreteTarget;
import com.frank.designpattern.observer.Observable;
import com.frank.designpattern.observer.Police;
import com.frank.designpattern.template.Hummer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: sb
 * @time: 2018-09-20 18:43
 * 门面模式
 * 外部访问子系统，都是通过门面对象，即门面对象是外部访问子系统的唯一通道。
 * 优点：
 * 1、灵活性好，屏蔽子系统，对外部系统透明，子系统变了不影响调用方。
 * 2、安全性高，外部系统想访问内部子系统只有一个入口，方便进行访问控制
 * 缺点：
 * 完全和子系统绑死，太依赖门面对象，一旦出错问题巨大（貌似什么代码都是这样--！）
 */
@Slf4j
public class Facade {

    /**
     * 子系统1
     */
    ConcreteTarget c = new ConcreteTarget();


    /**
     * 子系统2
     */
    Police p = new Police();

    /**
     * 子系统3
     */
    Hummer h = new Hummer();

    public void targetOperation() {
        log.info("门面模式假装校验。。。");
        this.c.targetOperation();
    }

    public void action(Observable.Notice notice) {
        log.info("门面模式假装校验。。。");
        this.p.action(notice);
    }

    public void run() {
        log.info("门面模式假装校验。。。");
        /**
         *
         * this.p.action(notice);
         * this.h.run();
         * 上边的写法不应出现在门面中，门面对象不参与业务逻辑
         * 如果真的需要处理子系统之间的业务逻辑，则封装一个新的对象
         *
         * class NewObject{
         *      Police p = new Police();
         *      Hummer h = new Hummer();
         *      public complexMethod(){
         *          this.p.action(notice);
         *          this.h.run();
         *      }
         * }
         *
         *
         */
        this.h.run(23);
    }


}
