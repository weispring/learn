package com.lxc.learn.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.DynamicIntroductionAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/14
 */
@Slf4j
public class Aop {

    public static void main(String[] args) {

/*

@Before
@AfterReturning
@AfterThrowing
@After //相当于try-catch-finally中的final，一般用于释放资源
@Around()


DynamicIntroductionAdvice
MethodInterceptor
ThrowsAdvice
AfterReturningAdvice
*/


       //AspectJAwareAdvisorAutoProxyCreator.postProcessAfterInitialization
       //org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy()

        //AspectJAwareAdvisorAutoProxyCreator.postProcessAfterInitialization
    }
}
