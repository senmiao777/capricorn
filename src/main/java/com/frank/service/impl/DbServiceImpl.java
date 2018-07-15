package com.frank.service.impl;

import com.frank.entity.mysql.Benefit;
import com.frank.entity.mysql.User;
import com.frank.service.IBenefitService;
import com.frank.service.IDbService;
import com.frank.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/15 0015 下午 12:50
 */
@Service
@Slf4j
public class DbServiceImpl implements IDbService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IBenefitService benefitService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void update(User user, Benefit benefit) {
        User save = userService.save(user);
        log.info("DbServiceImpl update user={}",user);

        benefitService.save(benefit);
        log.info("DbServiceImpl update benefit={}",benefit);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("测试事务回滚");
    }

    @Override
    public void update(List<User> userList, Benefit benefit) {
        userService.batchSave(userList);
        log.info("DbServiceImpl update userList={}",userList.size());
        benefitService.save(benefit);
        log.info("DbServiceImpl update benefit={}",benefit);
    }
}
