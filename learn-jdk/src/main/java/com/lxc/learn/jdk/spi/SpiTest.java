package com.lxc.learn.jdk.spi;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/8
 */
@Slf4j
public class SpiTest {

    /**
     * SPI ，全称为 Service Provider Interface，是一种服务发现机制。
     * 它通过在ClassPath路径下的META-INF/services文件夹查找文件，加载文件里所定义的类。
     * jdbc的驱动加载便是运用了此原理
     * @param args
     */
    public static void main(String[] args) {
        //以来的核心类java.util.ServiceLoader.LazyIterator
        ServiceLoader serviceLoader = ServiceLoader.load(IServiceProvider.class);
        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            IServiceProvider serviceProvider = (IServiceProvider) iterator.next();
            serviceProvider.invoke();
        }
        //com.mysql.jdbc.Driver
        //如果在当前项目定义了java.sql.Driver文件，则会有多个相同配置文件，如何处理呢
    }
}
