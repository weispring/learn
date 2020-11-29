package com.lxc.learn.jdk.common;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/23 9:55
 */
public class StringTest {

    public static void main(String[] args) {
     /*   Float f1 = new Float(0.01);

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
*/


    }

    /**
     * String 不可变的好处
     *
     * 1. 可以缓存 hash 值,通过equals相等的String，hashCode也相等

     因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。不可变的特性可以使得 hash 值也不可变，因此只需要进行一次计算。

     2. String Pool 的需要

     如果一个 String 对象已经被创建过了，那么就会从 String Pool 中取得引用。只有 String 是不可变的，才可能使用 String Pool。



     3. 安全性

     String 经常作为参数，String 不可变性可以保证参数不可变。例如在作为网络连接参数的情况下如果 String 是可变的，那么在网络连接过程中，String 被改变，改变 String 的那一方以为现在连接的是其它主机，而实际情况却不一定是。

     4. 线程安全

     String 不可变性天生具备线程安全，可以在多个线程中安全地使用。
     */

    private static String a = "0";

    private static String b = "0";

    private static String c = "0";

    /**
     * StringBuilder.append()与String的"+"的效率PK
     * 如果String通过"+"来拼接，如果拼接的字符串是常量，则效率会非常高，因为会进行编译时优化，这个时候StringBuilder的append()是达不到的。
     *
     * 如果将String的"+"放在循环中，会创建很多的StringBuilder对象，并且执行之后会调用toString()生成新的String对象，这些对象会占用大量的内存空间
     * 而导致频繁的GC，从而效率变慢。
     *
     * StringBuilder.append()中间过程中产生的垃圾内存大多数都是小块的内存，锁产生的垃圾就是拼接的对象以及扩容原来的空间(当发生String的"+"操作时，
     * 前一次String的"+"操作的结果就成了内存垃圾，垃圾会越来越多，最后扩容也会产生很多垃圾)
     *
     * 注意的是，并不是String的"+"操作本身慢，而是因为大循环中大量的内存使用，开销比较大，会导致频繁的GC，并且很多时候程序慢是因为频繁GC导致的
     * 而且更多的是FULL GC，效率才会下降。
     *
     * 如果是少量的小字符串叠加，那么采用append()提升效率并不明显，但是遇到大量的字符串叠加或者大字符串叠加的时候，使用append的效率会高很多。
     *
     * 最后有一个优化常识：
     * 在JVM中，提倡的重点是让这个"线程内所使用的内存"尽快结束，以便让JVM认为它是垃圾，在Young空间就尽量释放掉，尽量不要让其进入Old区域，一个
     * 重要的因素是代码是否跑得够快，其次是分配的空间要足够小。
     *
     * 当然优化也要看场景，世事无绝对。
     *
     * markERROR https://blog.csdn.net/m0_37536626/article/details/81180315
     *
     * 二
     *
     * java 中“+”操作会生成StringBuilder，StringBuilder的toString方法会new 一个新对象
     */
    @Test
    public void test(){
        String d = a + b + c;
        //class中  String d = new StringBuilder().append(a).append(b).append(c).toString();
        List<String> list = new ArrayList<>(100);
        for (int i=0; i<100; i++){
            list.add(UUID.randomUUID().toString());
        }
        String template = "<tr><td>{num}</td><td>{phone}</td><td>{plan}</td><td>{sellerPhone}</td></tr>\n";
        String content = "";
        Long start = System.currentTimeMillis();
        for (String s : list){
            content = content + template.replaceAll("num",s);
        }
        System.out.println("+ 耗时：" + (System.currentTimeMillis() - start));

        StringBuilder sb = new StringBuilder();
        start = System.currentTimeMillis();
        for (String s : list){
            sb.append(template.replaceAll("num",s));
        }
        content = sb.toString();
        System.out.println("append 耗时：" + (System.currentTimeMillis() - start));
    }

    private static final String STRING_TEST = "123456789";

    @Test
    public void test1(){
        List<String> list = new ArrayList<>(100);
        for (int i=0; i<100; i++){
            list.add(STRING_TEST);
        }
        String template = "<tr><td>{num}</td><td>{phone}</td><td>{plan}</td><td>{sellerPhone}</td></tr>\n";
        String content = "";
        Long start = System.currentTimeMillis();
        for (String s : list){
            content = content + template;
        }
        System.out.println("+ 耗时：" + (System.currentTimeMillis() - start));

        StringBuilder sb = new StringBuilder();
        start = System.currentTimeMillis();
        for (String s : list){
            sb.append(template);
        }
        content = sb.toString();
        System.out.println("append 耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test2() throws InterruptedException {
        String a = "a";
        String b = "b";
        String c = "c";
        int times = 1000000;//100000000
        int sleep = 1000*10;
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            String string = a + b + c;
            if (string.equals("abc")) {}
        }
        System.out.println("string+ cost time:" + (System.currentTimeMillis() - start) + "ms");

        Thread.sleep(sleep);

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(a);
            stringBuffer.append(b);
            stringBuffer.append(c);
            String string = stringBuffer.toString();
            if (string.equals("abc")) {}
        }
        System.out.println("stringbuffer cost time:" + (System.currentTimeMillis() - start) + "ms");

        Thread.sleep(sleep);

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(a);
            stringBuilder.append(b);
            stringBuilder.append(c);
            String string = stringBuilder.toString();
            if (string.equals("abc")) {}
        }
        System.out.println("stringbuilder cost time:" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * String 有大小限制
     * 1.堆内存大小
     * 2.字符串数据结构是char[]数组，因此数组长度int有限制。
     */
}
