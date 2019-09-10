package com.lxc.learn.junit.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: lixianchun
 * @Date: 2019/9/8 21:19
 * @Description:
 */
@Slf4j
public class FactoriesService {
    private String a = "";

    public FactoriesService(){
        log.info("初始化：{}",this.getClass().getName());
    }
}
