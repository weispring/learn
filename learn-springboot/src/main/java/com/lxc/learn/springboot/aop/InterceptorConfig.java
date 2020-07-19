package com.lxc.learn.springboot.aop;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lixianchun
 * @Date: 2020/7/19 15:32
 * @Description:
 */
@Configuration
public class InterceptorConfig {



    public static final String cutPointExpression = "execution(* com.lxc.learn.springboot.controller.*.*(..))";


    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor2() {
        LogAspect interceptor = new LogAspect();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(cutPointExpression);

        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }


}
