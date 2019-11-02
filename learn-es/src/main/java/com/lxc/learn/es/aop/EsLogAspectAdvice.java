package com.lxc.learn.es.aop;

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
public class EsLogAspectAdvice implements MethodInterceptor {


    /**
     * ERROR
     * bean需要被Spring管理，生成代理对象
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("测试:{}", this.getClass().getName());
        return invocation.proceed();
    }
}
