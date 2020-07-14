package com.lxc.learn.springboot.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect implements MethodInterceptor{


    // 切点
    @Pointcut("@annotation(com.lxc.learn.springboot.aop.ServiceLog)")
    public void executePointCut() {
    }


    @After("executePointCut()")
    public void afterReturning(JoinPoint point){
        String methodName = point.getSignature().getName();
        List<Object> args = Arrays.asList(point.getArgs());
        log.info("连接点方法为：" + methodName + ",参数为：" + args );
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        method.invoke(o,objects);
        methodProxy.invoke(objects,objects);
        return null;
    }
}
