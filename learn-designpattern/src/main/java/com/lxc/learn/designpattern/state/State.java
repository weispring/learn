package com.lxc.learn.designpattern.state;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
@Getter
@Setter
public abstract class State {

    private String userName;

    public abstract void Handle(ContextFlow context);

}

