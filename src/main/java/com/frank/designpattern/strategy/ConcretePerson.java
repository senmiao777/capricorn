package com.frank.designpattern.strategy;

import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: somebody
 * @time: 2019-05-07 17:57
 *
 * 策略模式的另一种实现方式：
 * 调用者不是接口或者抽象类，只有一种实现，是个普通的类
 * 持有策略接口的引用，在构造方法里把具体的策略传进来
 *
 * 实际例子：
 * 当tasks cannot be executed by a {@link ThreadPoolExecutor}的时候，设置 {@link RejectedExecutionHandler}
 */
@Component
public class ConcretePerson {

    private Fightable fightable;

    public ConcretePerson(Fightable fightable) {
        this.fightable = fightable;
    }

    public void strategyMethod(){
        fightable.fight("ConcretePerson strategyMethod");
    };

}
