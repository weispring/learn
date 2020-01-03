package com.lxc.learn.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:11
 */
@Slf4j
@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DataSourceHealthIndicatorAutoConfiguration.class},
                        scanBasePackages = {"com.lxc.learn.springboot"})
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            log.error("----{}----",key);
            /**
             * java.util.concurrent.Executor Executor
             *org.springframework.core.task.TaskExecutor TaskExecutor
             */
            Object object = context.getBeanFactory().getBean(key);
            if (object instanceof Executor || object instanceof TaskExecutor){
                log.error("object :{}",object);
            }
        }

    }
}
