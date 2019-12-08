package com.lxc.learn.jdk.exception;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/8 18:35
 * @Description:
 */
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
     *
     * 2. 运行期异常
     * 编译的时候可以通过，但是在运行的时候产生的异常叫做运行期异常。
     * 所有RuntimeException的子类都是运行期的异常，编译器无法检查。
     *
     * 问题：
     * 1.什么类型的异常需要继续向外抛出，什么样的不需要，为什么 throw new RunTimeException() 不需要抛出？
     * 编译期异常需要处理

     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("");
        FileInputStream fis = new FileInputStream(file);
    }


    public void login() throws LoginException{
        throw new LoginException();
    }

    public void login1(){
        throw new RuntimeException();
    }
}