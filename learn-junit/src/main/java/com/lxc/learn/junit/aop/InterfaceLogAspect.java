/*
package com.lxc.learn.junit.aop;


import cn.vpclub.moses.common.api.dto.log.InterfaceLogInfoDTO;
import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
public class InterfaceLogAspect {


    // 切点
    @Pointcut("@annotation(com.lxc.learn.junit.aop.BossInterfaceLog)")
    public void executePointCut() {
    }

    */
/*
     * @param pjp
     * @param interfaceLog
     * @return
     *//*



    @Around("executePointCut()")
    public Object arround(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        BossInterfaceLog interfaceLog = (BossInterfaceLog)method.getAnnotation(BossInterfaceLog.class);

        Object[] objs = pjp.getArgs();
        Object o = null;
        Long start = System.currentTimeMillis();
        try {
            o = pjp.proceed(objs);
        } catch (Throwable throwable) {
            log.error(throwable.getLocalizedMessage(), throwable);
        }
        if (interfaceLog == null){
            return o;
        }

        return  o;
    }

}
*/
