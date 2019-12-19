package com.lxc.learn.designpattern.facade;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class FacadeTest {


    /**

     > 外观模式 定义：提供一个统一的接口，用来访问子系统中的一群接口，外观定义了一个高层的接口，让子系统更容易使用。
     **其实就是为了方便客户的使用，把一群操作，封装成一个方法。**


     1. 何时使用
     客户端不需要知道系统内部的复杂联系，整个系统只提供一个“接待员”即可
     客户端不与系统耦合，外观类与系统耦合

     2. 优点
     减少了系统的相互依赖
     提高了灵活性。不管系统内部如何变化，只要不影响到外观对象，任你自由活动
     提高了安全性。想让你访问子系统的哪些业务就开通哪些逻辑，不在外观上开通的方法，你就访问不到
     3. 缺点
     不符合开不原则，修改很麻烦

     4. 使用场景
     为一个复杂的模块或子系统提供一个外界访问的接口,子系统相对独立，外界对子系统的访问只要黑箱操作即可
     预防低水平人员带来的风险扩散
     */
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.methodA();
    }
}
