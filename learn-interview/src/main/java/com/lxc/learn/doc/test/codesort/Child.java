package com.lxc.learn.doc.test.codesort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Child extends Parent{

    private static String sub = new String("100");//只执行

    static {
        log.info("zi类静态代码块");//只执行一次
    }

    {
        log.info("zi类代码块");
    }

    public Child(){
        log.info("zi类构造方法");
    }

    public static void main(String[] args) {
        new Child();
        new Child();
    }


    /**
     * 执行顺序
     * 父类静态代码块
     * zi类静态代码块
     * 父类代码块
     * 父类构造方法
     * zi类代码块
     * zi类构造方法
     */
}
