package com.lxc.learn.jdk.reflect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/20
 */
@Slf4j
public class ReflectNote {

    /**
     * java 反射
     * 它允许运行中的 Java 程序对自身进行检查，或者说“自审”，并能直接操作程序的内部属性和方法。
     *
     *
     * 通过反射加载类的使用场景
     * 1.运行时通过反射加载类，不确定当前类是否存在，若一定存在可import 导入；或者开发环境不存在，但是线上环境存在时;或者加载网络传输过来的类。
     * 2.动态灵活加载类，将类名字符串存储至.properties文件
     *
     * 通过反射创建对象和调用属性或者方法
     * 如：BeanUtils 对象属性copy 和 spi
     *
     */
    static {
        try {
            Class.forName("");
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

}
