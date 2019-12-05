package com.lxc.learn.designpattern.singleton;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/5 20:11
 * @Description:
 */
public class Singleton {
    private static volatile Singleton singleton = null;

    //私有构造方法
    private Singleton() {

    }

    //静态工厂方法

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized(Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
