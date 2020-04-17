package com.lxc.learn.junit.test.api;

import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.UserService;
import com.lxc.learn.junit.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 21:01
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setName("lichun");
        user.setEmail("2629469408@qq.com");
        user.setPhone("13403979090");
        userService.add(user);
        log.info("执行方法");
    }

    @BeforeClass
    public static void beforeClass(){
        log.info("{}","before class");
    }

    @AfterClass
    public static void afterClass(){
        log.info("{}","after class");
    }


    @After
    public void after(){
        log.info("{}","after");
    }


    @Before
    public void before(){
        log.info("{}","before");
    }

}
