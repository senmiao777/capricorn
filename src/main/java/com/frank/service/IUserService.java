package com.frank.service;

import com.frank.entity.mysql.User;

import java.util.List;

/**
 * Created by sb on 2018/7/18.
 */
public interface IUserService {
    User save(User user);

    boolean batchSave(List<User> userList);
}
