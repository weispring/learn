package com.lxc.learn.redis.controler;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.redis.entity.User;
import com.lxc.learn.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/19
 */
@Slf4j
@RequestMapping("/userCache")
@RestController
public class UserCacheControl {

    @Autowired
    private UserService userService;

    public User getUser(){
        return userService.findUserById(1);
    }

}
