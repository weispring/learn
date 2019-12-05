package com.lxc.learn.designpattern.singleton;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/5 20:10
 * @Description:
 */
public class HungerSingleton {

    /**
     * 单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。
     * 注意：
     * 1、单例类只能有一个实例。
     * 2、单例类必须自己创建自己的唯一实例。
     * 3、单例类必须给所有其他对象提供这一实例。

     http://www.imooc.com/article/256422
     *
     */
    //私有构造方法
    private HungerSingleton(){}

    private static final HungerSingleton singleton = new HungerSingleton();
    //静态工厂方法
    public static HungerSingleton getInstance(){
        return singleton;
    }
}
