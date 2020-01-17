package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/16
 */

/**
 *VM Args：-XX：PermSize=10M-XX：MaxPermSize=10M
 *@author zzm
 */
@Slf4j
public class JavaMethodAreaOOM{
    public static void main(String[]args){
        while(true){
            Enhancer enhancer=new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor(){
                public Object intercept(Object obj, Method method, Object[]args, MethodProxy proxy)throws Throwable{
                    return proxy.invokeSuper(obj,args);
                }}
            );
            Object o = enhancer.create();
            log.info("{}","");
        }
    }

    static class OOMObject{
    }
}
