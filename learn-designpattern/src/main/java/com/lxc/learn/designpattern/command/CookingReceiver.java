package com.lxc.learn.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
@Slf4j
public class CookingReceiver implements Receiver {
    @Override
    public void receice(Object req) {
        log.info("做饭");
    }
}
