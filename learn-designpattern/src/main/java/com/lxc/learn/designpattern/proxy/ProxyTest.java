package com.lxc.learn.designpattern.proxy;

import com.lxc.learn.designpattern.builder.Product;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/29
 */
@Slf4j
public class ProxyTest {


    /**
     *
     * 代理模式的定义与特点
     代理模式的定义：由于某些原因需要给某对象提供一个代理以控制对该对象的访问。这时，访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象之间的中介。

     代理模式的主要优点有：
     代理模式在客户端与目标对象之间起到一个中介作用和保护目标对象的作用；
     代理对象可以扩展目标对象的功能；
     代理模式能将客户端与目标对象分离，在一定程度上降低了系统的耦合度；

     其主要缺点是：
     在客户端和目标对象之间增加一个代理对象，会造成请求处理速度变慢；
     增加了系统的复杂度；
     代理模式的结构与实现
     代理模式的结构比较简单，主要是通过定义一个继承抽象主题的代理来包含真实主题，从而实现对真实主题的访问，下面来分析其基本结构和实现方法。
     1. 模式的结构
     代理模式的主要角色如下。
     1.抽象主题（Subject）类：通过接口或抽象类声明真实主题和代理对象实现的业务方法。
     2.真实主题（Real Subject）类：实现了抽象主题中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象。
     3.代理（Proxy）类：提供了与真实主题相同的接口，其内部含有对真实主题的引用，它可以访问、控制或扩展真实主题的功能。

     代理模式的应用场景
     前面分析了代理模式的结构与特点，现在来分析以下的应用场景。
     远程代理，这种方式通常是为了隐藏目标对象存在于不同地址空间的事实，方便客户端访问。例如，用户申请某些网盘空间时，会在用户的文件系统中建立一个虚拟的硬盘，用户访问虚拟硬盘时实际访问的是网盘空间。
     虚拟代理，这种方式通常用于要创建的目标对象开销很大时。例如，下载一幅很大的图像需要很长时间，因某种计算比较复杂而短时间无法完成，这时可以先用小比例的虚拟代理替换真实的对象，消除用户对服务器慢的感觉。
     安全代理，这种方式通常用于控制不同种类客户对真实对象的访问权限。
     智能指引，主要用于调用目标对象时，代理附加一些额外的处理功能。例如，增加计算真实对象的引用次数的功能，这样当该对象没有被引用时，就可以自动释放它。
     延迟加载，指为了提高系统的性能，延迟对目标的加载。例如，Hibernate 中就存在属性的延迟加载和关联表的延时加载。

     代理模式的扩展
     在前面介绍的代理模式中，代理类中包含了对真实主题的引用，这种方式存在两个缺点。
     真实主题与代理主题一一对应，增加真实主题也要增加代理。
     设计代理以前真实主题必须事先存在，不太灵活。采用动态代理模式可以解决以上问题，如 SpringAOP
     *
     */
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Proxy proxy = new Proxy(realSubject);
        proxy.disposal();
    }
}
