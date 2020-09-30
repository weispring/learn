package com.lxc.learn.junit.aop;

import java.lang.annotation.*;

/**
 * 不加元注解的话，org.aopalliance.intercept.MethodInterceptor的实现中，无法获取method 上的注解信息
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

    String value()  default  "";;
    String businessName() default  "";

}
