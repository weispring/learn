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

    /**
     * String 不可变的好处
     *
     * 1. 可以缓存 hash 值

     因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。不可变的特性可以使得 hash 值也不可变，因此只需要进行一次计算。

     2. String Pool 的需要

     如果一个 String 对象已经被创建过了，那么就会从 String Pool 中取得引用。只有 String 是不可变的，才可能使用 String Pool。



     3. 安全性

     String 经常作为参数，String 不可变性可以保证参数不可变。例如在作为网络连接参数的情况下如果 String 是可变的，那么在网络连接过程中，String 被改变，改变 String 的那一方以为现在连接的是其它主机，而实际情况却不一定是。

     4. 线程安全

     String 不可变性天生具备线程安全，可以在多个线程中安全地使用。
     */
}
