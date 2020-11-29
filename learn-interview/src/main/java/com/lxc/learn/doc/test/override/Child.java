package com.lxc.learn.doc.test.override;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Child extends Parent {

    //can't override static method, 除非前面加上static
    static void test(){
        System.out.println("child.test()");
    }


    public static void main(String[] args) {
        Parent parent = new Child();
        parent.test();//输出Parent.test()
        log.info("{}", parent.testMethod());
    }

    /**
     * 结论：
     * 会产生这个结果的原因显而易见，子类重写了父类的静态方法，但是并没有将其覆盖，二者之间仍然独立，
     * 所以类型是父类Super的变量，调用的仍然是父类的静态方法，而不是子类的重写方法。也就是说子类并不能重写父类的静态方法。

     * 解释：
     * 我上网查了一些别人的博客，理解了其中的原因。
     * 首先要明确一个概念，静态方法属于类，在编译阶段类被加载时，类的静态方法或者属性就会被分配内存，
     * 存储到类所在的内存中（堆内存的方法区中）；而类的非静态方法却是属于对象的，
     * 每个对象都有一份非静态方法的引用，并且若方法被重写，引用的就是子类重写的方法，且这是在运行时创建；
     * 正因如此，即使子类重写了父类的静态方法，但是本质上它们还是两个独立的类，在内存中分别占用不同的内存，
     * 它们的静态方法也是在编译时就被加载，独立的占用着不同的内存。上面的代码中，一个父类的变量，指向子类的对象，
     * 调用一个被子类重写的静态方法时，由于这是一个父类的变量，并且静态方法属于类，所以在调用时，
     * JVM会去父类所在的内存中寻找这个方法，所以最终的结果就是调用父类的方法，而不是子类重写的方法。
     */


    /**
     * 方法重写，方法名、入参和出参必须一致，出参可以是子类
     * @return
     */
    @Override
    public Long testMethod() {
        return new Long(11);
    }


    /**
     * 构造函数可以省略不写，构造函数方法名必须与类同名且不能有返回值 （void也不行），普通方法也可以和类名相同，
     * 但是要有返回值或void， 构造函数可以重载。
     */
    public void Child(){

    }
}
