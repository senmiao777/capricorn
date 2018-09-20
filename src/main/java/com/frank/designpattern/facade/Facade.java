package com.frank.designpattern.facade;

import com.frank.designpattern.adapter.ConcreteTarget;
import com.frank.designpattern.observer.Observable;
import com.frank.designpattern.observer.Police;
import com.frank.designpattern.template.Hummer;

/**
 * @author: sb
 * @time: 2018-09-20 18:43
 * 门面模式
 * 外部访问子系统，都是通过门面对象，即门面对象是外部访问子系统的唯一通道
 */
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

    public void targetOperation(){
        this.c.targetOperation();
    }

    public void action(Observable.Notice notice){
        this.p.action(notice);
    }

    public void run(){
        this.h.run();
    }


}
