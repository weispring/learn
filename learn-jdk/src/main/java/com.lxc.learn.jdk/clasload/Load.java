package com.lxc.learn.jdk.clasload;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Launcher;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/29 17:54
 */
@Slf4j
public class Load {

    /**
     * 重要方法：java.lang.ClassLoader#loadClass(java.lang.String)
     * @param args
     */
    public static void main(String[] args) {

        new Load().loadClass();
    }

    private void loadClass(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            classLoader.loadClass("com.lxc.learn.jdk.basedatatype.AddAddReduceReduce");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        log.info("{}", 1);
    }


    /**
     * BootStrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库，
     * 如：rt.jar、resources.jar、charsets.jar等，可通过如下程序获得该类加载器从哪些地方加载了相关的jar或class文件
     */
    @Test
    public void testBootStrapClassLoader(){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
        System.out.println(System.getProperty("sun.boot.class.path"));
    }


    /**
     * Launcher ExtClassLoader AppClassLoader
     */
    @Test
    public void testClassLoadTree(){
        ClassLoader loader = Load.class.getClassLoader();
        while (loader!=null){
            System.out.println(loader.toString());
            loader = loader.getParent();
        }
        System.out.println(loader);
    }




}
