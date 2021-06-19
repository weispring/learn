package com.lxc.learn.jdk.jna;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/11 19:18
 */
@Slf4j
public class JNITest {


    /**
     * 需要将.dll文件放到java.library.path目录下
     */
    static {
        System.out.println(System.getProperty("java.library.path"));
        //
        System.loadLibrary("test");
    }

    //此方法为链接库中的方法
    public native int add(int a,int b);
    public native int substract(int a,int b);
    public native void printHello();

    public static void main(String[] args) {
        //调用
        int sum = JNATest.Clibrary.INSTANTCE.add(5,3);
        int sub = JNATest.Clibrary.INSTANTCE.substract(5,3);
        System.out.println("add(5,3) = "+sum);
        System.out.println("substract(5,3) = "+sub);
        JNATest.Clibrary.INSTANTCE.printHello();
    }

}
