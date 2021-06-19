package com.lxc.learn.jdk.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/23
 */
@Slf4j
public class MethodResult {


    public <R> R sendAndWait(Object o, long l, TimeUnit timeUnit) {
        Method[] methods = this.getClass().getDeclaredMethods();
        return (R)get();
    }

    private Object get(){
        return 1000L;
    }

    public static void main(String[] args) {
        Long a = new MethodResult().sendAndWait(null,0,TimeUnit.SECONDS);
        System.out.print(a);
    }

}
