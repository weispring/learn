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
/**
 * 定义当前的状态
 * @author gh
 *
 */
@Setter
@Getter
public class ContextFlow {

    //11 21 31 正常流程
    //10 20 30 异常流程
    private Integer reviewStatus;

    private String request;

    private String message;

    State state;

    private boolean finished;

    public ContextFlow(State state) { //定义Context的初始状态
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
        log.info("当前状态为:{}",state);
    }

    public void request(){
        state.Handle(this); //对请求做处理并且指向下一个状态
    }
}

