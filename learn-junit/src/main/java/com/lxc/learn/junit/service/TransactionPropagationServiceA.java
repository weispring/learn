package com.lxc.learn.junit.service;

import com.lxc.learn.junit.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/31 18:30
 */

public class TransactionPropagationServiceA {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void p01(int b){

    }


}
