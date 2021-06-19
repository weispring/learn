package com.lxc.learn.jdk.common;

/**
 * @Description
 * @Date 2021-02-16
 * @Created by lixianchun
 */
public class TestInnerClass {
    private int outer;

    //  非静态内部类
    public class InnerClass {
        int a = outer + 1;
        private InnerClass (int b) {
        }
    }

    //    静态内部类
    private static class InnerStaticClass {

    }

    public void method() {
        int y = 12;
//      局部类
        class LocalClass {
            int localY = y;
            {
                //y = 13; //编译不通过
                localY = 1;
            }
        }
    }

    public static void main(String[] args) {
    }

    /**
     * 静态内部类、非静态内部类、局部类的比较？
     *
     * 非静态内部类字节码也记录了外部类的信息
     * 编译器帮我们增加了一个外部类的成员变量final com.test.TestInnerClass this$0;, 静态内部类没有这个成员变量, 这也解释了为什么在非静态内部类中可以访问外部类的非静态成员变量
     * 编译器同时也帮我们修改了构造器(构造器不存在则新增构造器), private com.test.TestInnerClass$InnerClass(com.test.TestInnerClass, int);, 我们源码中构造器只传递了一个int型参数, 编译器帮我们在第一个位置插入了一个外部类的参数com.test.TestInnerClass, 并将其设置到this$0成员变量. 确保整个非静态内部中, 都可以通过this$0使用到外部类的成员变量
     * 由于非静态内部类构造器的第一个参数是外部类的实例, 所以在创建非静态内部类前, 必须有一个对应的外部类实例. (静态内部类无此要求)
     *
     * 局部类字节码也记录了外部类的信息, 已经所在方法的名称
     * 编译器帮我们生成的两个成员变量: final int val$y;和final com.test.TestInnerClass this$0;
     * val$y, 接收method方法中的局部变量y的值, 注意, val$y被final修饰, 这也就是说我们在局部类中不可以修改外部方法(method)中的局部变量值, 所以y = 13;这句编译不通过
     * this$0, 与非静态内部类一样, this$0这个参数用于指向外部类的实例, 因此局部类中也是可以访问外部类的非静态成员变量
     * this$0, val$y这两个成员变量通过编译器新增的构造器com.test.TestInnerClass$1LocalClass(this$0, val$y);传入
     *
     * 总结
     * 静态内部类, 和普通类类似, 只是引用方式采用外部类.内部类
     * 非静态内部类, 和外部类实例有强关联, 内部维护了一个指向外部类实例的成员变量, 如果内部类没有引用外部类的成员变量, 可以采用静态内部类实现. 节省一个指向外部类实例的成员变量的内存空间
     * 局部类, 在某个方法内部创建, 可以引用外部类的成员和方法的局部变量, 但不可以修改方法的局部变量值
     */
}


