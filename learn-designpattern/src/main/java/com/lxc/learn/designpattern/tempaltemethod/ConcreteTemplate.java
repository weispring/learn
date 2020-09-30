package com.lxc.learn.designpattern.tempaltemethod;

/**
 * @Auther: lixianchun
 * @Date: 2020/9/13 10:35
 * @Description:
 */
public class ConcreteTemplate extends TemplateMethodAbstract{

    //基本方法的实现
    @Override
    public void abstractMethod() {
        //业务相关的代码
    }

    //重写父类的方法
    @Override
    public void hookMethod() {
        //业务相关的代码
    }
}
