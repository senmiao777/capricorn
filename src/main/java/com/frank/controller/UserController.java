package com.frank.controller;

import com.frank.annotation.Encrypt;
import com.frank.annotation.MDCLog;
import com.frank.annotation.RedisLock;
import com.frank.entity.mysql.TestUser;
import com.frank.entity.mysql.User;
import com.frank.model.JsonResult;
import com.frank.repository.mysql.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 基本增删改查操作
 * 缺少权限验证
 * 以及各种校验
 */
@Slf4j
@Encrypt
@RestController
@RequestMapping(value = "/t/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @MDCLog
    @RedisLock(key="#this[0]")
    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public JsonResult getUser(@PathVariable Long id) {
        log.info("getUser id={}", id);
        User one = userRepository.findOne(id);
        return JsonResult.buildSuccessResult(one);

    }

    @MDCLog
    @RedisLock(key="#this[0]")
    @RequestMapping(value = "/{id}/suffix", method = RequestMethod.GET)
    public JsonResult getUserSuffix(@PathVariable Long id) {
        log.info("getUserSuffix id={}", id);
        User one = userRepository.findOne(id);
        return JsonResult.buildSuccessResult(one);

    }

    /**
     * 根据id删除用户
     * 应该做权限验证，防止攻击
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/d", method = RequestMethod.POST)
    public JsonResult deleteUser(@RequestParam Long id) {

        User user = userRepository.findOne(id);

        if (null == user) {
            log.info("deleteUser user not exist.id ={}", id);
        }
        // 进行业务逻辑判断，比如前置状态等等
        userRepository.delete(id);

        return JsonResult.buildSuccessResult(null);

    }

    /**
     * 新建用户
     * 应该做权限验证，防止攻击
     *
     * @param
     * @return
     */
    @RedisLock(perfix = "testPerfix", key = "#this[2]")
    @RequestMapping(value = "/c", method = RequestMethod.POST)
    public JsonResult createUser(@RequestParam String userName,
                                 @RequestParam Integer age,
                                 @RequestParam String phone) {

        log.info("createUser userName={},age={}", userName, age);

        try {
            Class.forName("big.sister.Whatever");
        } catch (ClassNotFoundException e) {
            log.error("Class.forName e={}", ExceptionUtils.getStackTrace(e));
            // return JsonResult.buildErrorResult("人为异常");
        }
        ClassLoader classLoader = UserController.class.getClassLoader();

        try {
            Class<?> aClass = classLoader.loadClass("big.sister.Whatever");
        } catch (ClassNotFoundException e) {
            log.error("classLoader.loadClass e={}", ExceptionUtils.getStackTrace(e));
            return JsonResult.buildErrorResult("人为异常");
        }
        Map m;

        User user = new User();
        user.setAge(age);
        user.setUserName(userName);
        user.setUserType(1);
        user.setPhone(Long.valueOf(phone));
        user = userRepository.save(user);
        log.info("createUser userId={}", user.getId());
//        throw new ResubmitException("justfortest");
        return JsonResult.buildSuccessResult(user);

    }


    @RequestMapping(value = "/entity/c", method = RequestMethod.POST)
    public JsonResult createUser(TestUser u) {

        log.info("TestUser user={}", u);


        return JsonResult.buildSuccessResult(u);

    }




    /**
     * 更新用户
     * 应该做权限验证，防止攻击
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/u", method = RequestMethod.POST)
    public JsonResult updateUser(@RequestParam Long id,
                                 @RequestParam(required = false) String userName,
                                 @RequestParam(required = false) String age) {


        log.info("updateUser id={},userName={},age={}", id, userName, age);

        User user = userRepository.findOne(id);
        log.info("create ={}", user.getCreateAt());
        if (null == user) {
            log.info("updateUser can not find user.id={},userName={},age={}", id, userName, age);
            return JsonResult.buildSuccessResult("根据此id未查询到用户", user);
        }
        log.info("updateUser user={}", user);
        if (StringUtils.isNotBlank(userName)) {
            user.setUserName(userName);
        }
        if (NumberUtils.isDigits(age)) {
            user.setAge(Integer.valueOf(age));
        }
        log.info("date ={}", new Date());
        user.setUpdateAt(new Date());
        User save = userRepository.save(user);
        log.info("after update user = {}", save);


        return JsonResult.buildSuccessResult(save);

    }

}
