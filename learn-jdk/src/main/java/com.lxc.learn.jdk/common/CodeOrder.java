package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

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
        new CodeOrder();
        new CodeOrder();
        //输出

/*        09:15:39.629 [main] INFO com.lxc.learn.jdk.common.CodeOrderParent - P静态代码块：
        09:15:39.634 [main] INFO com.lxc.learn.jdk.common.CodeOrder - 静态代码块：
        09:15:39.634 [main] INFO com.lxc.learn.jdk.common.CodeOrderParent - P构造代码块：
        09:15:39.634 [main] INFO com.lxc.learn.jdk.common.CodeOrderParent - P构造函数：
        09:15:39.634 [main] INFO com.lxc.learn.jdk.common.CodeOrder - 构造代码块：
        09:15:39.634 [main] INFO com.lxc.learn.jdk.common.CodeOrder - 构造函数：*/
    }
}
