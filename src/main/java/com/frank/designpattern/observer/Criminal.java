package com.frank.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 被观察者-罪犯
 */
@Slf4j
public class Criminal extends AbstractSubject {

    private int age;
    private String name;

    public void sleep(String time) {
        log.info("Criminal sleep {}", time);
        /**
         * 被观察者要等待观察者处理完成之后才能继续往下走，考虑性能问题
         * 这个例子，主题通知观察者的时候，数据是通过“推”的形式给观察者的，即主题一次性将所有数据传给观察者们，不管他们要不要，要多少数据
         * 观察者获取数据还可以通过“拉”的形式，即观察者收到某一种类型的事件后，主动来主题查询。这样观察者可以按需获取自己需要的数据。
         * 不过“拉”的方式需要主题做好权限控制，什么数据能被观察者拉取，什么数据不能被拉取需要做好控制。
         */
        notifyObservers(new Notice(OperationType.SLEEP, this.getClass().getSimpleName(), time));
        log.info("do next things");
    }

    public int getAge() {
        return age;
    }

    /**
     * 当年龄发生修改了，通知注册的观察者
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
        notifyObservers(new Notice(OperationType.UPDATE_AGE, this.getClass().getSimpleName(), age));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
