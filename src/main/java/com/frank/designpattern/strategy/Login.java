package com.frank.designpattern.strategy;

import com.frank.entity.mysql.User;

/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/8/22
 **/
public class Login {


    public static void main(String[] args) {

        String type;


    }

    private User getUser(String type) {
        if ("github".equals(type)) {
            // do something
            return new User("getUser from github");
        } else if ("gitee".equals(type)) {
            // do something
            return new User("getUser from gitee");
        } else {
            return null;
        }
    }
}
