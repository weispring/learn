package com.lxc.learn.designpattern.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/29
 */
@Slf4j
public class RealSubject implements ISubject {

    @Override
    public void disposal() {
        log.info("执行代理：{}");
    }

}
