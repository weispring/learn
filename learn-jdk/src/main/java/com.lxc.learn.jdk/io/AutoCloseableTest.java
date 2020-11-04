package com.lxc.learn.jdk.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 资源类
 *
 * */
@Slf4j
public class AutoCloseableTest implements AutoCloseable {
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public void close() throws Exception {
        System.out.println("AutoCloseableTest is closed");
    }

    /**
     * 不用显示在finally 中关闭资源，编译为class 后，会自动加上finally
     * jdk7及以后关闭流的方式
     *
     * 一个对象在没有被引用变量指向的时候它会变成垃圾，最终会被垃圾回收器从内存中清除，
     对于我们创建的流对象，干嘛还要"调用close方法将其进行关闭呢，以释放与其相关的资源"呢？

     当我们在程序中创建一个IO流对象的时候，同时系统也会创建
     一个叫做流的东西，在这种情况下，计算机内存中实际产生了两个事物，一个是java程
     序中类的实例对象，一个是系统本身产生的某种资源，而java垃圾回收器只能管理程序
     中类的实例对象，没办法去管理系统产生的资源，所以程序需要调用close方法，去通
     知系统释放其自身产生的资源
     * */
    public static void main(String[] args) {
        //需要实现接口 AutoCloseable
        try(AutoCloseableTest resource = new AutoCloseableTest()) {
            resource.sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * try finally 带来的问题
     * 第一个问题就是如果我们有不止两个资源，比如有十个资源，难道需要让我们写十个嵌套的语句吗？写完之后这个代码还能看吗？
     第二个问题就是如果我们在try里面出现异常，然后在finally里面又出现异常，就会导致异常覆盖，会导致finally里面的异常将try的异常覆盖了。
     * @throws IOException
     */
    private static void testClose() throws IOException {
        InputStream inputStream = new FileInputStream("file");
        OutputStream outStream = null;
        try {
            log.info("{}", inputStream.read(new byte[4]));
            outStream = new FileOutputStream("file1");
            outStream.write(new byte[4]);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            try {
                if (outStream != null){
                    outStream.close();
                }
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }
            inputStream.close();
        }
    }


    private static void closeException(){
        throw new RuntimeException("close");
    }

    /**
     * 会产生异常覆盖，只输出 closeException 产生的异常
     */
    public static void test() {
        try {
            try{
                throw new RuntimeException("doSomething");
            }finally {
                closeException();
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 解决措施
     * try-with-resources语句其实是一种语法糖，通过编译之后又回到了我们开始说的嵌套的那种模式。
     * 前提需要实现接口 AutoCloseable
     */

    public void test2() {
        try(AutoCloseableTest closeTest = new AutoCloseableTest();
            AutoCloseableTest closeTest1 = new AutoCloseableTest();){
            throw new RuntimeException("Something");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
