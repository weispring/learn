package com.lxc.learn.jdk.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/23 9:55
 */
public class StringTest {

    public static void main(String[] args) {



        Float f1 = new Float(0.01);

        Float f2 = new Float(0.02);

        Float f3 = new Float(0.03);
        System.out.println(f3 - f2 == f2 - f1);

        System.out.println(f1.equals(f2));
        System.out.println(f1.equals(f1));

        BigDecimal a1 = new BigDecimal("0.8");
        BigDecimal b = new BigDecimal("0.9");
        BigDecimal c = new BigDecimal("0.8");

        BigDecimal d = new BigDecimal("0.8");
        BigDecimal x = a1.subtract(b);// 0.1
        BigDecimal y = b.subtract(c); // 0.1
        System.out.println(d.equals(a1));// true

        //放入常量池
        String str1 = "12345";
        //指向常量池
        String str2 = "12345";
        //开辟新的地址
        String str3 = new String("12345");
        if (str1 == str2){
            System.out.println("1");
        }
        if (str1==str3){
            System.out.println("2");
        }
        if (str3 == str2){
            System.out.println("3");
        }


        String a = "";

        //初始容量16，扩容2n+2
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("11");
        //无缓存，创建新char[]
        stringBuilder.toString();


    }
}
