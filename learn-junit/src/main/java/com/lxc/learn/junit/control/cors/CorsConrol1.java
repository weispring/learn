package com.lxc.learn.junit.control.cors;

import com.lxc.learn.junit.aop.ApiLog;
import com.lxc.learn.junit.aop.Second;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.UserService;
import com.lxc.learn.junit.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/14 19:38
 * @Description:
 */
@RequestMapping(value = "/cors")
@RestController
//@AllArgsConstructor
public class CorsConrol1 {

    @RequestMapping(method = RequestMethod.GET, value = "/cors1")
    String cors1(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-origin","http://localhost:9999");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        return "cors2";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cors2")
    String cors2(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-origin","http://localhost:9990");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        return "cors2";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cors3")
    User cors3(@RequestBody User user){

        return user;
    }

    @Autowired
    private UserServiceImpl userService;
/*
    private String name;
@Autowired属性上的该注解可以通过构造函数实现注入，但属性上的@value注解，
无法通过构造函数注入，除非在构造函数上也加上该注解，而@Autowired就不用。
也就是说@values注解只能在实例化后实现。

    public CorsConrol1(UserService userService,@Value("${spring.datasource.name}") String name){
        this.userService = userService;
        this.name = name;
    }*/

    public CorsConrol1(UserServiceImpl userService){
        this.userService = userService;
        System.out.println("00000000000000009999");
    }
}
