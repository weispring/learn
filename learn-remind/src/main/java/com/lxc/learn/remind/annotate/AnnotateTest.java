package com.lxc.learn.remind.annotate;

import java.lang.annotation.*;

/**
 * @Auther: lixianchun
 * @Date: 2019/5/1 21:51
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface AnnotateTest {

    String consumerGroup() default "";
}
