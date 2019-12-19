package com.lxc.learn.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class Adapter implements Target {

    /**
     组件
     目标(Target)角色：客户端所期待得到的接口，
     适配器(Adaper)角色：适配器类是本模式的核心。适配器把源接口转换成目标接口。显然，这一角色不可以是接口，而必须是具体类。
     源(Adapee)角色被适配者：现在需要被适配的接口

     应用场景
     后期维护，由于不同的厂家不同的产品以及不同的开发人员
     调用第三方组件
     双方都不太容易修改的时候

     用途
     想要复用一些现有的类，但是接口与复用环境要求不一致

     适配器模式与装饰模式的对比
     适配器模式
     持有的是待适配的对象，实现的是目标接口，两个不一样
     扩展了待适配类新的功能
     适配器模式拓展了新的功能
     装饰器模式
     持有对象和继承的对象一般是同一个类或接口
     装饰模式中装饰过的类力求与原来对外接口一致，这使得在调用方看来，装饰过后的类与之前没有什么区别，只是某一方面功能增强了
     装饰模式增强了原有功能
     总结 装饰模式与适配器模式是不冲突的，可以既增强原有功能，又添加全新的功能。

     */
    private ThirdParty thirdParty;

    @Override
    public void invoke(Object request) {
        log.info("适配器类");
    }

    public Adapter(ThirdParty thirdParty){
        this.thirdParty = thirdParty;
    }
}
