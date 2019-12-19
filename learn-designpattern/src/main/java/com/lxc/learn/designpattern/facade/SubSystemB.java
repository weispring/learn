package com.lxc.learn.designpattern.facade;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class SubSystemB {

    public void dosomethingB(){
        log.info("{} dosomethingB ",this.getClass().getSimpleName());
    }

}
