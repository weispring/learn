package com.lxc.learn.doc.test.override;

public class Parent {

    static void test(){
        System.out.println("Parent.test()");
    }

    public Object testMethod(){
        return new Integer(10);
    }

}
