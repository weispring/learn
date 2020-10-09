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

}
