package com.lxc.learn.web.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 缓存不一致处理办法
 * 1.设置失效时间，运行缓存短时间不一致的情况，此法就可
 * 2.每次修改数据后，延迟再次清除缓存，通过监听binglog或者aop实现
 * @author lixianchun
 * @description
 * @date 2020/10/22
 */

@Aspect
@Component
@Slf4j
public class CacheDelayClearAspect {

    // 切点
    @Pointcut("@annotation(org.springframework.cache.annotation.CacheEvict)")
    public void executePointCut() {
    }


    @Around("executePointCut()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("延迟再次清除缓存{}", this.getClass().getName());
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        Object object = pjp.proceed();
        CacheConfig annotation = AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), CacheConfig.class);
        //todo 提交到一个延时队列中，后续线程池去清理,待完成
        log.info("清除缓存：{}", annotation.cacheNames());
        return object;
    }


}
