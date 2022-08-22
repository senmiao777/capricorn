package com.frank.designpattern.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/8/22
 **/
public class UserStrategyFactory2 {
    static List<UserStrategy.GithubUserStrategy> strategies = new ArrayList() {{
        this.add(new UserStrategy.GithubUserStrategy());
        this.add(new UserStrategy.GiteeUserStrategy());
    }};

    public static UserStrategy getStrategy(String type) {
        for (UserStrategy userStrategy : strategies) {
            if (userStrategy.supportType(type)) {
                return userStrategy;
            }
        }
        return null;
    }
}
