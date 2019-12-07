package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/12/5 14:34
 */
@Slf4j
public class CodeOrderParent {

    static {
        log.info("P静态代码块：{}", "");
    }


     {
        log.info("P构造代码块：{}", "");
    }


    public CodeOrderParent(){
        log.info("P构造函数：{}", "");
    }
}
