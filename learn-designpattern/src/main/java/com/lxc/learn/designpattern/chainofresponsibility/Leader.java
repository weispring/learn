package com.lxc.learn.designpattern.chainofresponsibility;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/29
 */
@Slf4j
public abstract class Leader {

    //抽象处理者：领导类
    private Leader next;

    public void setNext(Leader next) {
        this.next=next;
    }

    public Leader getNext() {
        return next;
    }
    //处理请求的方法
    public abstract boolean handleRequest(int LeaveDays);

}
