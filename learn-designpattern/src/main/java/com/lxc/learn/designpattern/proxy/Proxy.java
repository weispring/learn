package com.lxc.learn.designpattern.proxy;

import com.lxc.learn.designpattern.builder.Product;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/29
 */
@Slf4j
public class Proxy implements ISubject {

    private ISubject subject;

    public Proxy(ISubject subject){
        this.subject = subject;
    }

    private void before(){
        log.info("代理前：{}");
    }

    @Override
    public void disposal() {
        before();
        subject.disposal();
        after();
    }

    private void after(){
        log.info("代理后：{}");
    }
}
