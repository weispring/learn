package com.lxc.learn.designpattern.prototype;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/27
 */
@Slf4j
public class PrototypeTest {

    /**
     * 原型模式的定义与特点
     原型（Prototype）模式的定义如下：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象。
     在这里，原型实例指定了要创建的对象的种类。用这种方式创建对象非常高效，根本无须知道对象创建的细节。

     原型模式的结构与实现
     由于 Java 提供了对象的 clone() 方法，所以用 Java 实现原型模式很简单。

     1. 模式的结构
     原型模式包含以下主要角色。
     抽象原型类：规定了具体原型对象必须实现的接口。
     具体原型类：实现抽象原型类的 clone() 方法，它是可被复制的对象。
     访问类：使用具体原型类中的 clone() 方法来复制新的对象。

     原型模式的应用场景
     原型模式通常适用于以下场景。
     对象之间相同或相似，即只是个别的几个属性不同的时候。
     对象的创建过程比较麻烦，但复制比较简单的时候。

     */
    public static void main(String[] args)throws CloneNotSupportedException {
        Realizetype obj1 = new Realizetype();
        Realizetype obj2 = (Realizetype)obj1.clone();
        System.out.println("obj1==obj2?"+(obj1==obj2));
    }

    //具体原型类
    public static class Realizetype implements Cloneable {
        public Realizetype() {
            System.out.println("具体原型创建成功！");
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            System.out.println("具体原型复制成功！");
            return super.clone();
        }
    }

}
