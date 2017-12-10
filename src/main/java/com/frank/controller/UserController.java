package com.frank.controller;

import com.frank.entity.mysql.User;
import com.frank.model.JsonResult;
import com.frank.repository.mysql.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 基本增删改查操作
 * 缺少权限验证
 * 以及各种校验
 */
@Slf4j
@RestController
@RequestMapping(value = "/t/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;


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
    @RequestMapping(value = "/c", method = RequestMethod.POST)
    public JsonResult createUser(@RequestParam String userName,
                                 @RequestParam Integer age) {


        log.info("createUser userName={},age={}", userName, age);

        User user = new User();
        user.setAge(age);
        user.setUserName(userName);
        user.setUserType(1);
        user = userRepository.save(user);

        return JsonResult.buildSuccessResult(user);

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
        if(null == user){
            log.info("updateUser can not find user.id={},userName={},age={}", id, userName, age);
            return JsonResult.buildSuccessResult("根据此id未查询到用户",user);
        }
        if(StringUtils.isNotBlank(userName)){
            user.setUserName(userName);
        }
        if(NumberUtils.isDigits(age)){
            user.setAge(Integer.valueOf(age));
        }
        user = userRepository.save(user);

        return JsonResult.buildSuccessResult(user);

    }

}
