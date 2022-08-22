package com.frank.designpattern.strategy;

import com.frank.entity.mysql.User;


public interface UserStrategy {
    // 获取对应平台的用户信息
    User getUser(String type);
    // 是否支持该类型
    boolean supportType(String type);

    class GithubUserStrategy implements UserStrategy {

        @Override
        public User getUser(String type) {
            return new User("getUser from github");
        }
        @Override
        public boolean supportType(String type) {
            return "github".equals(type);
        }
    }

    class GiteeUserStrategy implements UserStrategy {
        public User getUser(String type) {
            return new User("getUser from gitee");
        }
        @Override
        public boolean supportType(String type) {
            return false;
        }
    }
}
