package com.lxc.learn.jdk.jna;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/11 19:06
 */
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * Created by zhonghao.han on 9/12/2018.
 */
public class JNATest {

    /**
     * .dll 编译为64为，则jdk 版本必须为64位
     * 相对路径不行时，用绝对路径试试
     * Java 调用有三种方式，JNative、JNA、JNI
     */

    //继承Library，用于加载库文件
    public interface Clibrary extends Library {
        //绝对路径，相对路径不知为何没有起作用
        JNATest.Clibrary INSTANTCE = (JNATest.Clibrary) Native.loadLibrary((Platform.isWindows() ? "F:\\learn\\learn-jdk\\src\\main\\resources\\test.dll" : "c"), JNATest.Clibrary.class);

        //此方法为链接库中的方法
        int add(int a,int b);
        int substract(int a,int b);
        void printHello();
    }

    public static void main(String[] args) {
        //调用
        int sum = JNATest.Clibrary.INSTANTCE.add(5,3);
        int sub = JNATest.Clibrary.INSTANTCE.substract(5,3);
        System.out.println("add(5,3) = "+sum);
        System.out.println("substract(5,3) = "+sub);
        JNATest.Clibrary.INSTANTCE.printHello();
    }
}
