package com.frank.designpattern.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/8/22
 **/
public class UserStrategyFactory {
    private static final Map<String, UserStrategy> strategies = new HashMap<>();
    static {
        strategies.put("github", new UserStrategy.GithubUserStrategy());
        strategies.put("gitee", new UserStrategy.GiteeUserStrategy());
    }
    public static UserStrategy getStrategy(String type) {
        return strategies.get(type);
    }
}
