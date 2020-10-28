package com.lxc.learn.junit.transaction;

import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/12 20:11
 */
@Slf4j
@Service
public class ServiceA extends BaseService {


    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRequired(){
        userMapper.insert(create("REQUIRED"));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveSupports(){
        userMapper.insert(create("SUPPORTS"));
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMandatory(){
        userMapper.insert(create("MANDATORY"));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRequiresNes(){
        userMapper.insert(create("REQUIRES_NEW"));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveNotSupportted(){
        userMapper.insert(create("NOT_SUPPORTED"));
    }


    @Transactional(propagation = Propagation.NEVER)
    public void saveNever(){
        userMapper.insert(create("NEVER"));
    }


    @Transactional(propagation = Propagation.NESTED)
    public void saveNested(){
        userMapper.insert(create("NESTED"));
    }

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUser(){
        User user = new User();
        user.setId(3);
        user.setName("哈哈哈，required_new");
        userMapper.updateById(user);
    }

    @Override
    protected String getName() {
        return this.getClass().getSimpleName();
    }
}
