package com.lxc.learn.junit.control;

import com.lxc.learn.common.util.SpringContextHolder;
import com.lxc.learn.junit.config.CustomerConfig;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.UserService;
import com.lxc.learn.junit.service.impl.UserServiceImpl;
import com.lxc.learn.junit.spring.BeanAutoConfig;
import com.lxc.learn.junit.spring.BeanDefine;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.NioEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
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
@RequestMapping(value = "/test02")
@RestController
@Slf4j
@Order
public class UserControl {
    @Autowired
    private Environment environment;
    @Autowired
    private CustomerConfig customerConfig;
    @Value("${test.arr[0]}")
    private String arr;

    @Value("${test.arr[1]}")
    private String arr1;

    @Autowired
    private BeanAutoConfig beanAutoConfig;




    @RequestMapping(value = "/test")
    public String test(@RequestBody User user){
        //TODO ERROR org.apache.tomcat.util.wirelessnet.NioEndpoint;
        log.trace("日志级别--trace");
        log.debug("日志级别--debug");
        log.info("日志级别--info");

        log.info("test.same.a{}", environment.getProperty("test.same.a"));

        //不存在
        List list = environment.getProperty("test.arr",List.class);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test";
    }

    private String a = "1";
    @RequestMapping(value = "/testSleep10s")
    public String testSleep10s() throws InterruptedException {
      /*  if (true){
            throw new RuntimeException("000000000000");
        }*/

        log.info("throw new RuntimeException(\"000000000000\");");
        Thread.sleep(1000*10);
        log.info("日志级别--info");
        return "testSleep10s";
    }


}
