package com.lxc.learn.web.servicelimiting;

import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.common.util.SpringContextHolder;
import com.lxc.learn.common.util.web.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.lxc.learn.web.servicelimiting.GenerateToken.TOKEN_QUEUE;


@Aspect
@Component
@Slf4j
public class ServiceLimitAspect {

    @Autowired
    private SpringContextHolder springContextHolder;


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

        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        ServiceLimit serviceLimit = (ServiceLimit)method.getAnnotation(ServiceLimit.class);
        if (serviceLimit == null){
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        AbstractLimitAllow abstractLimitAllow;
        if (StringUtil.isNotEmpty(serviceLimit.value())){
            abstractLimitAllow = SpringContextHolder.getBean(serviceLimit.value());
        }else {
            abstractLimitAllow = (AbstractLimitAllow)SpringContextHolder.getBean(serviceLimit.type());
        }
        if (abstractLimitAllow == null){
            log.error("限流配置异常：method:{},ServiceLimit value:{},type:{}",method.getName(),serviceLimit.value(),serviceLimit.type().getName());
            throw new RuntimeBusinessException("限流配置异常：method: " + method.getName() + ",ServiceLimit value "+ serviceLimit.value() +",type:{}");
        }


/*        if (GenerateToken.getLastRequstTime() + 1000/5 > System.currentTimeMillis()){
            throw new RuntimeException("request too frequently");
        }*/
        try {
            o = pjp.proceed(objs);
        } catch (Throwable throwable) {
            log.error(throwable.getLocalizedMessage(), throwable);
        }

        return o;
    }
}
