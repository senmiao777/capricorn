package com.frank.service.impl;

import com.frank.entity.mysql.Benefit;
import com.frank.entity.mysql.Stock;
import com.frank.entity.mysql.User;
import com.frank.service.IBenefitService;
import com.frank.service.IDbService;
import com.frank.service.IStockService;
import com.frank.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    private IStockService stockService;

    @Autowired
    private IBenefitService benefitService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(User user, Benefit benefit) {
        User save = userService.save(user);
        log.info("DbServiceImpl update user={}", user);

        benefitService.save(benefit);
        log.info("DbServiceImpl update benefit={}", benefit);

        throw new RuntimeException("测试事务回滚");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(List<User> userList, Benefit benefit) {
        benefitService.save(benefit);
        log.info("DbServiceImpl update benefit={}", benefit);
        userService.batchSave(userList);
        log.info("DbServiceImpl update userList={}", userList.size());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUserAndStock(User user, Stock stock) {

        userService.save(user);
        stockService.saveWithRuntimeException(stock);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUserAndStockWithException(User user, Stock stock) {
        userService.save(user);
        try {
            stockService.saveWithRuntimeException(stock);
        } catch (Exception e) {
            log.error("stock={},e={}", stock.getCode(), ExceptionUtils.getStackTrace(e));
        }
    }
}
