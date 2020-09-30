package com.frank.service;

import com.frank.entity.mysql.User;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/15 0015 下午 12:31
 */
public interface IUserService {

    User save(User user);

    boolean batchSave(List<User> userList);

    int updatePhone(Long phone, Long id);

    User findByPhone(Long phone);

    User findAndUpdate(Long phone);


    User update(User user);
}
