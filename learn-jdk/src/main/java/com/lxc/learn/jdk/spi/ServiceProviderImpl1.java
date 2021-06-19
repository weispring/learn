package com.lxc.learn.jdk.spi;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/8
 */
@Slf4j
public class ServiceProviderImpl1 implements IServiceProvider {
    @Override
    public String getType() {
        return "2";
    }

    @Override
    public void invoke() {
        log.info("this name : {} ",this.getClass().getName());
    }
}
