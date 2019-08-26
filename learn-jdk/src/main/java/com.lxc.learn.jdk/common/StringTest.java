package com.lxc.learn.jdk.common;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/23 9:55
 */
public class StringTest {

    public static void main(String[] args) {
        String a = "";

        //初始容量16，扩容2n+2
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("11");
        //无缓存，创建新char[]
        stringBuilder.toString();


    }
}
