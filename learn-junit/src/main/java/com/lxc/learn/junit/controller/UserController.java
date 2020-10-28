package com.lxc.learn.junit.controller;


import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.aop.ApiLog;
import com.lxc.learn.junit.aop.Second;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.resp.OrderDetailResp;
import com.lxc.learn.junit.service.impl.UserServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {


    private static UserServiceImpl userServiceImpl;

    /**
     * 通过非静态方法注入静态属性
     * @param userServiceImpl
     */
    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        UserController.userServiceImpl = userServiceImpl;
    }

    //org.springframework.boot.autoconfigure.condition.OnClassCondition
    @RequestMapping(value = "/add")
    public User add(@RequestBody User user){
        return userServiceImpl.add(user);
    }

    @ApiLog
    @Second
    @RequestMapping(value = "/list")
    public List<User> list(@RequestBody User user){
        List list = userServiceImpl.list();
        return list;
    }


    @RequestMapping(value = "/getUserDetail")
    public User getUserDetail(@RequestBody Req<Long> req){
        return userServiceImpl.getUserDetail(req.getBody());
    }

}

