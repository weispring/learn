package com.lxc.learn.jdk.proxy.javaagent;

import sun.misc.Launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/19
 **/
public class Test {

    public static void test(){
        System.out.println("===========================");
    }
    public void test123(){
        System.out.println("===========================");
    }

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Test.class.getClassLoader();
        //从classpath(包括项目路径)查找[java/lang/Byte.class],可以在项目自建同名类java/lang/Byte
        Enumeration<URL> paths  = classLoader.getResources("com/lxc/learn/jdk/proxy/javaagent/Main.class");
        System.getProperty("java.class.path");

        while (paths.hasMoreElements()){
            System.out.println(paths.nextElement());
        }
    }
}
