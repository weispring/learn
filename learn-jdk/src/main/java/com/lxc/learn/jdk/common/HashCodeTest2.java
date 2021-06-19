package com.lxc.learn.jdk.common;

/**
 * @Description
 * @Date 2021-02-16
 * @Created by lixianchun
 */
public class HashCodeTest2 {

    /**
     * 在64位jvm中, hashCode最大占用31个bit; 32位jvm中, hashCode最大占用25个bit
     * hashCode一共有六种生成策略
     * 通过-XX:hashCode=2这种形式, 可以验证上述的5中hashCode生成策略
     * @param args
     */
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        System.out.println(o1.hashCode());
        System.out.println(o2.hashCode());
    }
}
