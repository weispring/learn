package com.lxc.learn.temp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncAnnotationAdvisor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/17
 */
@Slf4j
@EnableAsync
@Async
public class EnableAsyncTest {

    //https://cloud.tencent.com/developer/article/1497604
    //org.springframework.scheduling.annotation.AsyncConfigurationSelector
    //org.springframework.scheduling.annotation.ProxyAsyncConfiguration
    //ProxyAsyncConfiguration AsyncExecutionInterceptor

    //AsyncAnnotationAdvisor
    //AsyncExecutionAspectSupport 執行線程池選擇


    /**
     * 線程池選擇的優先級
     1.AsyncConfigurer bean
     2.@EnableAsync 中的value對於的bean
     3.return (Executor)beanFactory.getBean(TaskExecutor.class);
     4.beanFactory.getBean("taskExecutor", Executor.class);
     5.new SimpleAsyncTaskExecutor()
     */

}
