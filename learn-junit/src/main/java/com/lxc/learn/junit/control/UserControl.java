package com.lxc.learn.junit.control;

import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:42
 * @Description:
 */
@RequestMapping(value = "/user")
@RestController
public class UserControl {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/add")
    public User add(@RequestBody User user){
        return userService.add(user);
    }


    @RequestMapping(value = "/list")
    public List<User> list(@RequestBody User user){
        return userService.list();
    }

}
