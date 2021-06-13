package com.lxc.learn.jdk.exception;

import lombok.extern.slf4j.Slf4j;
import org.omg.PortableServer.THREAD_POLICY_ID;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/8 18:35
 * @Description:
 */
@Slf4j
public class TestException {

    /**
     * java.lang.Throwable
     * 所有已实现的接口：
     * Serializable
     * 直接已知子类：
     * Error, Exception
     *
     *
     * Error是throwable的子类，他是程序出现严重的问题，这种问题程序解决不了。
     * 如：因为内存溢出
     * 错误都是以Error为结尾
     *
     *
     * 异常
     * 1. 编译期异常，除了RuntimeException都是编译期的异常
     * 编译期的异常我们必须处理，如果我们不处理就会编译失败。我们必须通过程序来处理编译期的异常
     * 我们两种解决方案：抛出异常，把异常抛给调用当前方法的层内部处理，catch来处理。
     * CheckedException
     * 除了RuntimeException以外的异常，都属于checkedException，它们都在java.lang库内部定义。Java编译器要求程序必须捕获或声明抛出这种异常。
     * 一个方法必须通过throws语句在方法的声明部分说明它可能抛出但并未捕获的所有checkedException。
     * Java.lang.ClassNotFoundException
     * Java.lang.CloneNotSupportedException
     * Java.lang.IllegalAccessException
     * Java.lang.InterruptedException
     * Java.lang.NoSuchFieldException
     * Java.lang.NoSuchMetodException

     *
     * 2. 运行期异常
     * 编译的时候可以通过，但是在运行的时候产生的异常叫做运行期异常。
     * 所有RuntimeException的子类都是运行期的异常，编译器无法检查。
     * RuntimeException在默认情况下会得到自动处理。所以通常用不着捕获RuntimeException，但在自己的封装里，也许仍然要选择抛出一部分RuntimeException。
     *
     * 问题：
     * 1.什么类型的异常需要继续向外抛出，什么样的不需要，为什么 throw new RunTimeException() 不需要抛出？
     * 编译期异常需要处理

     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("");
        autoPrintLog();
        FileInputStream fis = new FileInputStream(file);
    }


    public void login() throws LoginException{
        throw new LoginException();
    }

    public void login1(){
        throw new RuntimeException();
    }


    /**
     * 主动打印堆栈的两种方式
     * 1.构造异常
     * 2.通过当前线程
     */
    private static void autoPrintLog(){
        println(Thread.currentThread().getStackTrace());
        Throwable throwable = new Throwable();
        Throwable e = new Throwable("业务日志");
        int a = 22;
        log.error(e.getMessage(), e);
    }


    private static void println(StackTraceElement[] stackElements){
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                System.out.print(stackElements[i].getClassName() + "." + stackElements[i].getMethodName());
                System.out.println("(" + stackElements[i].getFileName() + ":" + stackElements[i].getLineNumber() + ")");
            }
        }
    }



}
