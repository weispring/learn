package com.lxc.learn.redis.entity;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/19
 */
@Slf4j
@Getter
@Setter
public class User extends BaseDto{

    private Integer userId;

    private String name;

    private String phone;

    public User(Integer userId,String name){
        this.userId = userId;
        this.name = name;
    }
}
