package com.lxc.learn.jdk.clasload.testscanline;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/4/13
 **/
public class Test extends Parent{
    public static final String URL = "";

    public static final Integer PORT = 8080;

    public static void main(String[] args) throws IllegalAccessException {
        Class cla = Test.class;

        while (cla != Object.class){
            Field[] fields = cla.getDeclaredFields();
            for (Field f : fields){
                f.get(null);
                System.out.println(f.getName());
            }
            cla = cla.getSuperclass();
        }

    }

}
