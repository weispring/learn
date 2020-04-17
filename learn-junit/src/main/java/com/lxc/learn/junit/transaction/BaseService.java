package com.lxc.learn.junit.transaction;

import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/12 20:38
 */
@Slf4j
public abstract class BaseService {

    @Autowired
    protected UserMapper userMapper;

    abstract protected String getName();

    protected User create(String method){
        return create(method, null);
    }

    protected User create(String method,Integer id){
        User user = new User();
        user.setId(id);
        user.setName("lichun"+"-"+getName()+"-"+method);
        user.setEmail("2629469408@qq.com");
        user.setPhone("13403979090");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        return user;
    }

}
