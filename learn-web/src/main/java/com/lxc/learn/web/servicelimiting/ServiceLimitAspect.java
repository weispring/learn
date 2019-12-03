package com.lxc.learn.web.servicelimiting;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static com.lxc.learn.web.servicelimiting.GenerateToken.TOKEN_QUEUE;


//@Aspect
@Component
@Slf4j
public class ServiceLimitAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void aspectRequestMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void aspectPostMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void aspectGetMapping() {
    }


    /**
     * 此方法会引起 短时间窗口内超过峰值
     * @param pjp
     * @return
     */
    @Around("aspectRequestMapping() || aspectPostMapping() || aspectGetMapping()")
    public Object arround(ProceedingJoinPoint pjp) {
        Object[] objs = pjp.getArgs();
        Object o = null;
        Long start = System.currentTimeMillis();
        if (TOKEN_QUEUE.poll() == null){
            throw new RuntimeException("Service limit has reached the limit");
        }
        try {
            o = pjp.proceed(objs);
        } catch (Throwable throwable) {
            log.error(throwable.getLocalizedMessage(), throwable);
        }

        return o;
    }
}
