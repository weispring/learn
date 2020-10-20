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

        /**BootStrap ClassLoader ExtClassLoader AppClassLoader
       　1. <Java_Runtime_Home>/lib下的类；
     　　2. < Java_Runtime_Home >/lib/ext下或者由系统变量java.ext.dir指定位置中的类；
     　　3. 当前工程类路径下或者由系统变量java.class.path指定位置中的类。

         * 扩展类加载器（ExtClassLoader）的父类加载器被强制设置为null了，那么扩展类加载器为什么还能将加载任务委派给启动类加载器呢？
         * 标准扩展类加载器和系统类加载器及其父类（java.net.URLClassLoader和java.security.SecureClassLoader）
         * 都没有覆写java.lang.ClassLoader中默认的加载委派规则---loadClass（…）方法。
         * java.lang.ClassLoader中默认的加载委派规则，如果父加载器为null，则会调用本地方法进行启动类加载尝试
         *
         * 在代码中直接调用Class.forName(String name)方法，到底会触发那个类加载器进行类加载行为？
         * AppClassLoader
         *
         * 在编写自定义类加载器时，如果没有设定父加载器，那么父加载器是谁？
         　在不指定父类加载器的情况下，默认采用系统类加载器。
          众所周知，我们编写自定义的类加载器直接或者间接继承自java.lang.ClassLoader抽象类，对应的无参默认构造函数实现,父类加载器来自系统类加载器

         自定义类加载器，需要注意不要将class 文件放入 <Java_Runtime_Home>/lib

         如何在运行时判断系统类加载器能加载哪些路径下的类？
     　　一是可以直接调用ClassLoader.getSystemClassLoader()或者其他方式获取到系统类加载器（系统类加载器和扩展类加载器本身都派生自URLClassLoader），
         调用URLClassLoader中的getURLs()方法可以获取到。
     　　二是可以直接通过获取系统属性java.class.path来查看当前类路径上的条目信息 ：System.getProperty("java.class.path")。

         如何在运行时判断标准扩展类加载器能加载哪些路径下的类？
         public static void main(String[] args) {
         try {
         URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();
         for (int i = 0; i < extURLs.length; i++) {
         System.out.println(extURLs[i]);
         }
         } catch (Exception e) {
         //…
         }
         }
         */
        try {
            System.out.println(ClassLoader.getSystemClassLoader());
            System.out.println(ClassLoader.getSystemClassLoader().getParent());
            System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());

            Class c = Class.forName("com.lxc.learn.jdk.clasload.Test");
            System.out.println(">>" + c.getClassLoader());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
