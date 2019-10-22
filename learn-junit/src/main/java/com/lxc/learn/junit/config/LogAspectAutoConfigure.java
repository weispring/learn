package com.lxc.learn.junit.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAspectAutoConfigure {

	@Bean
	public LogAspectAdvice feignAspectAdvice() {
		return new LogAspectAdvice();
	}

	/**
	 * feign切面
	 * 
	 * @return
	 */
	@Bean
	public AspectJExpressionPointcut feignClientPointcut() {
		AspectJExpressionPointcut feignClientPointcut = new AspectJExpressionPointcut();
		// feign切面：如果没有配置，则取默认的
		feignClientPointcut.setExpression("execution( * com.lxc.learn.junit.service.UserService.list(..))");
		return feignClientPointcut;
	}

	@Bean
	public Advisor feignAdvisor(@Autowired LogAspectAdvice feignAspectAdvice,
                                @Autowired AspectJExpressionPointcut feignClientPointcut) {
		DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		feignAdvisor.setAdvice(feignAspectAdvice);
		feignAdvisor.setPointcut(feignClientPointcut);
		return feignAdvisor;
	}
}