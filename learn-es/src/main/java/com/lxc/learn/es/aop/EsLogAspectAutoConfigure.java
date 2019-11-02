package com.lxc.learn.es.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsLogAspectAutoConfigure {

	@Bean
	public EsLogAspectAdvice esLogAspectAdvice() {
		return new EsLogAspectAdvice();
	}

	/**
	 * feign切面
	 * 
	 * @return
	 */
	@Bean
	public AspectJExpressionPointcut logClientPointcut() {
		AspectJExpressionPointcut logClientPointcut = new AspectJExpressionPointcut();
		// feign切面：如果没有配置，则取默认的
		logClientPointcut.setExpression("execution( * org.elasticsearch.action.ActionRequestBuilder.execute(..))");
		//feignClientPointcut.setExpression("@annotation(com.lxc.learn.junit.aop.ApiLog)");
		return logClientPointcut;
	}

	@Bean
	public Advisor feignAdvisor(@Autowired EsLogAspectAdvice esLogAspectAdvice,
                                @Autowired AspectJExpressionPointcut logClientPointcut) {
		DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		feignAdvisor.setAdvice(esLogAspectAdvice);
		feignAdvisor.setPointcut(logClientPointcut);
		return feignAdvisor;
	}
}