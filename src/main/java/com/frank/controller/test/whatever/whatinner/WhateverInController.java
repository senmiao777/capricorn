package com.frank.controller.test.whatever.whatinner;

import com.frank.annotation.Encrypt;
import com.frank.annotation.MDCLog;
import com.frank.annotation.RedisLock;
import com.frank.entity.mysql.User;
import com.frank.model.JsonResult;
import com.frank.repository.mysql.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基本增删改查操作
 * 缺少权限验证
 * 以及各种校验
 */
@Slf4j
@Encrypt
@RestController
@RequestMapping(value = "/what/in/t/user")
public class WhateverInController {


    @Autowired
    private UserRepository userRepository;

    @MDCLog
    @RedisLock(key = "#this[0]")
    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public JsonResult getUser222(@PathVariable Long id) {
        log.info("WhateverInController getUser id={}", id);
        User one = userRepository.findOne(id);
        return JsonResult.buildSuccessResult(one);

    }


}
