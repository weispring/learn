package com.lxc.learn.redis.controler;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.redis.entity.User;
import com.lxc.learn.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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


    @RequestMapping("/testip")
    public void test(HttpServletRequest request){
        String ip;
        ip = request.getHeader("x-forwarded-for");
        log.info("ip:{}",ip);
        ip = request.getHeader("Proxy-Client-IP");
        log.info("ip:{}",ip);
        ip = request.getHeader("WL-Proxy-Client-IP");
        log.info("ip:{}",ip);
        ip = request.getHeader("HTTP_CLIENT_IP");
        log.info("ip:{}",ip);
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        log.info("ip:{}",ip);
        ip = request.getRemoteAddr();
        log.info("ip:{}",ip);
        ip = request.getHeader("X-Real-IP");
        log.info("ip:{}",ip);
    }

}
