package com.lxc.learn.designpattern.facade;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class Facade {

    //被委托的对象
    SubSystemA a;
    SubSystemB b;

    public Facade() {
        a = new SubSystemA();
        b = new SubSystemB();
    }

    //提供给外部访问的方法
    public void methodA() {
        this.a.dosomethingA();
    }

    public void methodB() {
        this.b.dosomethingB();
    }

}
