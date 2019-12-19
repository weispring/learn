package com.lxc.learn.designpattern.state;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class StateMain {

    public static void main(String[] args) {
        State state = new ConcreteStateA();
        ContextFlow context = new ContextFlow(state);
        context.setReviewStatus(11);
        context.setRequest("李某某 申请10000元活动经费");
        context.request();

        //
        context.setState(state);
        context.setReviewStatus(0);
        context.request();
    }
}
