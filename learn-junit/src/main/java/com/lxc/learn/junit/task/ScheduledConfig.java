package com.lxc.learn.junit.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
*
* spring Scheduled 线程池配置
 * @author lixianchun
 * @Description
 * @date 2019/6/19 18:06
*/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(destroyMethod="shutdown")
    public Executor setTaskExecutors(){
        // 多个 @Scheduled 可以做到并发执行
        return Executors.newScheduledThreadPool(4);
    }// 3个线程来处理。
}


