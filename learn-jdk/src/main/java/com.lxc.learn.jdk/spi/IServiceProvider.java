package com.lxc.learn.jdk.spi;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/8
 */
public interface IServiceProvider {

    public String getType();

    public void invoke();
}
