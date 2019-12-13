package com.lxc.learn.designpattern.singleton;

/**
 * 使用枚举，枚举本身是单例静态的，线程安全，没有懒加载
 *
 * @author Zhang Kai
 * @version 1.0
 * @since <pre>2018/3/21 17:03</pre>
 */
public enum SingletonDemoByEnum {
    INSTANCE;

    public static void main(String[] args) {
        SingletonDemoByEnum.INSTANCE.show();
    }

    public void show() {
        System.out.println("Hello World.");
    }
}
