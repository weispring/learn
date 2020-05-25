package com.lxc.learn.junit.util.multilanguage;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiLanguage {


    @AliasFor("param")
    boolean value() default  true;

    //是否处理入参
    @AliasFor("value")
    boolean param() default  true;

    boolean resp() default true;

}
