package com.lxc.learn.web.servicelimiting;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLimit {

    String value() default "funnelLimitAllow";


    Class type() default FunnelLimitAllow.class;
}
