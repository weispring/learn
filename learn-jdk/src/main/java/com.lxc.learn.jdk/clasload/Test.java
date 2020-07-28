package com.lxc.learn.jdk.clasload;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/24
 */
@Slf4j
public class Test {

    private String a = TestA.getRandomStr();

    public static void main(String[] args) {
        System.out.println("1-------");
        new Test();

        System.out.println("2-------");

        new Test();
        System.out.println("3-------");
    }

}
