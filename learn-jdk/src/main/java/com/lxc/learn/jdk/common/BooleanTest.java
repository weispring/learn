package com.lxc.learn.jdk.common;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/13
 **/
public class BooleanTest {

    public static void main(String[] args) {
        //null 可以转化为任何类型的对象，包括Boolean,但是不能转化为boolean,因为涉及到了拆箱
        Object obj = null;
        String h = (String) obj;

        Boolean o = (Boolean) obj;
        if (!(boolean)o){
            System.out.println("-------------[]");
        }
    }
}
