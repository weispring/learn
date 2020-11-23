package com.lxc.learn.doc.test.codesort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Parent {


    private static String s = new String("100");//只执行

    static {
        log.info("父类静态代码块");//只执行一次
    }

    {
        log.info("父类代码块");
    }

    public Parent(){
        log.info("父类构造方法");
    }

    /**
     * 总结执行先后顺序为：静态块/静态变量>构造块>构造方法。
     * @param args
     */
    public static void main(String[] args) {
        new Parent();
        new Parent();
    }

}
