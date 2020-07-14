package com;

/**
 * 资源类
 *
 * */
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
     * */
    public static void main(String[] args) {
        try(AutoCloseableTest resource = new AutoCloseableTest()) {
            resource.sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
