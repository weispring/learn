package com.lxc.learn.junit.mapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/3
 */
@Slf4j
public class CrecordMapperProvider {

    public String select(){
        return "select * from crecord where id = #{id}";
    }
}
