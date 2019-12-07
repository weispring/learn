package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/12/5 14:34
 */
@Slf4j
public class CodeOrder extends CodeOrderParent {

    static {
        log.info("静态代码块：{}", "");
    }


     {
        log.info("构造代码块：{}", "");
    }


    public CodeOrder(){
        log.info("构造函数：{}", "");
    }

    public static void main(String[] args) {
        //new CodeOrder();
    }
}
