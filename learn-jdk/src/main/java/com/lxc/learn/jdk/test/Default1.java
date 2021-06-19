package com.lxc.learn.jdk.test;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/19 21:53
 * @Description:
 */
public class Default1 {

    //只有同包才能调用
    Long id;

    /**
     * 同包或者当前类
     */
    void defaultMethod(){
        System.out.println("");
    }

}
