package com.frank.service.impl;

import com.frank.entity.mysql.User;
import com.frank.repository.mysql.UserRepository;
import com.frank.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public int updatePhone(Long phone, Long id) {
        return userRepository.updatePhone(phone, id);
    }

    @Override
    public User findByPhone(Long phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User findAndUpdate(Long phone) {

        User byPhone = userRepository.findByPhone(phone);
        log.info("byPhone={}", byPhone);
        int i = userRepository.updatePhone(13286230000L, byPhone.getId());
        log.info("修改条数={},id={}", i, byPhone.getId());
        log.info("after update byPhone={}", byPhone);
        byPhone.setAge(90);
        userRepository.save(byPhone);
        HibernateEntityManager hEntityManager = (HibernateEntityManager) entityManager;
        Session session = hEntityManager.getSession();
        session.evict(byPhone);
        byPhone.setAge(91);
        return byPhone;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User update(User user) {

        int i = userRepository.updatePhone(13286230001L, user.getId());
        log.info("修改条数={},id={}", i, user.getId());
        log.info("after update byPhone={}", user);
        user.setAge(88);
        userRepository.save(user);

        return user;
    }


    @Override
    public User findByUserIdFake(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setUserName("张三");
        user.setAge(18);
        user.setPhone(13240115678L);
        try {
            log.info("findByIdFake线程id={}",Thread.currentThread().getId());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
