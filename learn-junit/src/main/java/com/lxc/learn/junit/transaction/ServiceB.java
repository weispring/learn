package com.lxc.learn.junit.transaction;

import lombok.extern.slf4j.Slf4j;
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
public class ServiceB extends BaseService{


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
    public void saveRequiresNews(){
        userMapper.insert(create("REQUIRES_NEW"));
        //throw new RuntimeException("B-REQUIRES_NEW");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveNotSupportted(){
        userMapper.insert(create("NOT_SUPPORTED"));
        throw new RuntimeException("--");
    }


    @Transactional(propagation = Propagation.NEVER)
    public void saveNever(){
        userMapper.insert(create("NEVER"));
    }


    @Transactional(propagation = Propagation.NESTED)
    public void saveNested(){
        userMapper.insert(create("NESTED"));
        //throw new RuntimeException("B-NESTED");
    }

    @Override
    protected String getName() {
        return this.getClass().getSimpleName();
    }

}
