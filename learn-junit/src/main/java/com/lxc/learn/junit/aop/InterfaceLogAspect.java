package com.lxc.learn.junit.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
@Order(1)
public class InterfaceLogAspect {


    // 切点
    @Pointcut("@annotation(com.lxc.learn.junit.aop.ApiLog)")
    public void executePointCut() {
    }




    @Around("executePointCut()")
    public Object arround(ProceedingJoinPoint pjp) {
        log.info("{}", this.getClass().getName());
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        ApiLog apiLog = method.getAnnotation(ApiLog.class);
        if (apiLog == null){
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                log.error(throwable.getMessage(),throwable);
            }
        }
        Object[] objs = pjp.getArgs();
        Object o = null;
        Long start = System.currentTimeMillis();
        try {
            o = pjp.proceed(objs);
        } catch (Throwable throwable) {
            log.error(throwable.getLocalizedMessage(), throwable);
        }
        return  o;
    }

}
