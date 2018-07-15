package com.frank.service.impl;

import com.frank.entity.mysql.User;
import com.frank.repository.mysql.UserRepository;
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
 * @date 2018/7/15 0015 下午 12:33
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void transfer(User u1, User u2) {

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public boolean batchSave(List<User> userList) {
        log.info("batchSave userList.size={}", userList.size());
        int count = 1;
        for (User u : userList) {
            log.info("userList 第{}次保存", count);
            userRepository.save(u);
//            if(count == 3){
//                throw new RuntimeException("userList RuntimeException");
//            }
            count++;
        }
        return false;
    }
}
