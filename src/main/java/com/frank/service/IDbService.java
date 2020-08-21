package com.frank.service;

import com.frank.entity.mysql.Benefit;
import com.frank.entity.mysql.Stock;
import com.frank.entity.mysql.User;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/15 0015 下午 12:48
 */
public interface IDbService {

    /**
     * 事务测试
     * @param user
     * @param benefit
     */
    void update(User user, Benefit benefit);

    /**
     * 事务测试
     * @param userList
     * @param benefit
     */
    void update(List<User> userList, Benefit benefit);

    void saveUserAndStock(User user,Stock stock);

    void saveUserAndStockWithException(User user,Stock stock);

}
