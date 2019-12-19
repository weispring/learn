package com.lxc.learn.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class DecoratorTest {
    /**
     *定义
     在不改变原有对象的基础之上，将功能附加到对象上。提供了比继承更有弹性的替代方案（扩展原有对象功能）

     类型
     结构型

     适用场景
     扩展一个类的功能或者给一个类添加附加职责
     给一个对象动态的添加功能，或动态撤销功能。
     优点
     继承的有力补充，比继承灵活，不改变原有对象的情况下给一个对象扩展功能。（继承在扩展功能是静态的，必须在编译时就确定好，而使用装饰者可以在运行时决定，装饰者也建立在继承的基础之上的）
     通过使用不同装饰类以及这些类的排列组合，可以实现不同的效果。存在递归调用，可叠加
     符合开闭原则

     缺点
     会出现更多的代码，更多的类，增加程序的复杂性。
     动态装饰时，多层装饰时会更复杂。（使用继承来拓展功能会增加类的数量，使用装饰者模式不会像继承那样增加那么多类的数量但是会增加对象的数量，当对象的数量增加到一定的级别时，无疑会大大增加我们代码调试的难度）

     装饰者相关的设计模式
     装饰者和代理模式
     装饰者模式关注的是对象的动态添加功能。代理模式关注的是对对象的控制访问，对它的用户隐藏对象的具体信息。

     装饰者模式和适配器模式
     装饰者模式和被装饰的类要实现同一个接口，或者装饰类是被装饰的类的子类。 适配器模式和被适配的类具有不同的接口。

     */
    public static void main(String[] args) {
        ABattercake aBattercake;
        aBattercake = new Battercake();
        aBattercake = new EggDecorator(aBattercake);
        System.out.println(aBattercake.getDesc()+" 销售价格:"+aBattercake.cost());
        aBattercake = new SausageDecorator(aBattercake);
        System.out.println(aBattercake.getDesc()+" 销售价格:"+aBattercake.cost());

        aBattercake = new SausageDecorator(aBattercake);
        System.out.println(aBattercake.getDesc()+" 销售价格:"+aBattercake.cost());
    }

}
