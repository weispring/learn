package com.lxc.learn.designpattern.state;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class ConcreteStateE extends State{

    @Override
    public void Handle(ContextFlow context) {
        log.info("{} 驳回了申请 ： {}",this.getClass().getSimpleName(), context.getRequest());
    }
}

