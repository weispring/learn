package com.lxc.learn.junit.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/22 17:12
 */
@Slf4j
@Component
public class LogAspectAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("测试:{}", this.getClass().getName());
        return invocation.proceed();
    }
}
