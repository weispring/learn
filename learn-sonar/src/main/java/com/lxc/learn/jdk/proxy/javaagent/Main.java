package com.lxc.learn.jdk.proxy.javaagent;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/19
 **/
public class Main {

    public static void main(String[] args) {
        System.out.println("");
        test6();
        test88();
        Test.test();

        new Test().test123();
    }


    public static void test88(){
        System.out.println("88");
    }

    public static void test6(){
        System.out.println("6");
    }
}
