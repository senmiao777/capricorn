package com.frank.designpattern.strategy;

/**
 * @author frank
 * @version 1.0
 * @date 2019/3/3 0003 下午 6:45
 * 战斗策略接口，实现该接口的类就会具有战斗特性
 */
public interface Fightable {
    void fight(String name);

    /**
     * 感觉在这种类似特性的接口里添加多个方法并不好
     * 假设你有十个方法（其中的方法分别是m1,m2,m3...m10），那么你的实现类就要实现这十个方法。
     * 现在比如有20个实现类，其中18个实现类对m1方法的实现都是一样的，那么你就需要写18遍重复的m1
     * 你说好我把m1提取到一个抽象类，给个默认的实现方法。那m2怎么办，你给不给默认实现？
     * 假设有9个子类需要对m2有一种实现，8个子类需要对m2有另一种实现，那你怎么能避免不写重复代码？
     * 在加上m3呢,你说我排列组合，写N（排列组合的公式给忘了--！）个抽象类，你怕不是疯了
     *
     * 而方法单一，意味着粒度小，可复用率高，想在哪用在哪用
     */
    //void defence(String name);
}
