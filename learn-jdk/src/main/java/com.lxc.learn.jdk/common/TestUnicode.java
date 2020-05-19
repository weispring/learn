package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/18
 */
@Slf4j
public class TestUnicode {

    public static void main(String[] args) {
        //\u000d //解码为换行，打印语句正常执行
        // \u000d System.out.println("哈哈哈");

        byte[] bytes = "".getBytes();
        System.out.println(bytes.length);


    }
}
