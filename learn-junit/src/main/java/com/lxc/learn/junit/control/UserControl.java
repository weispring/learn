package com.lxc.learn.junit.control;

import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.NioEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
@Slf4j
public class UserControl {

    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;

    @Value("${test.arr[0]}")
    private String arr;

    @Value("${test.arr[1]}")
    private String arr1;

    @RequestMapping(value = "/add")
    public User add(@RequestBody User user){
        return userService.add(user);
    }


    @RequestMapping(value = "/list")
    public List<User> list(@RequestBody User user){
        return userService.list();
    }


    @RequestMapping(value = "/test")
    public void test(@RequestBody User user){
        //TODO ERROR org.apache.tomcat.util.net.NioEndpoint;
        log.trace("日志级别--trace");
        log.debug("日志级别--debug");
        log.info("日志级别--info");

        //不存在
        List list = environment.getProperty("test.arr",List.class);
        return;
    }

}
