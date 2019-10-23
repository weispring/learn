package com.lxc.learn.junit.aop;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

    String value()  default  "";;
    String businessName() default  "";

}
