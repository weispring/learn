package com.lxc.learn.jdk.modifier.test.test01;

import com.lxc.learn.jdk.modifier.test.Default1;
import com.lxc.learn.jdk.modifier.test.ProtectedA;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/4 23:16
 * @Description:
 */
public class ProtectedB extends ProtectedA{


    public void test000(){
        this.a = "";
        this.test();
    }

    public static void main(String[] args) {
        Default1 default1 = new Default1();
    }
}
