package com.lxc.learn.junit.util.multilanguage;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
@Order(1)
public class MultiLanguageAspect {

    // 切点
    @Pointcut("@annotation(com.lxc.learn.junit.util.multilanguage.MultiLanguage)")
    public void multiLanguagePointCut() {
    }

    @Around("multiLanguagePointCut()")
    public Object arround(ProceedingJoinPoint pjp) {
        try{
            //处理入参
            MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
            Method method = methodSignature.getMethod();
            MultiLanguage multiLanguage = (MultiLanguage)method.getAnnotation(MultiLanguage.class);
            if (multiLanguage.param()){
                MultiLanguageUtil.dispose(pjp.getArgs());
            }
            Object object = pjp.proceed();
            //处理返回值
            if (multiLanguage.resp()){
                MultiLanguageUtil.replaceMultiLanguage(object);
            }
            return object;
        }catch (Throwable e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
