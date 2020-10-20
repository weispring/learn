package com.lxc.learn.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
public interface Receiver {

    public void receice(Object req);

}
