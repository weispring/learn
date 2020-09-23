package com.lxc.learn.springboot;

import com.alibaba.druid.support.json.JSONUtils;
import com.lxc.learn.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

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

    /**
     * 添加System Property需要按照-Dname=value的形式指定，并且需要注意的是，这个参数需要放在Java和-jar命令的中间，否则是没有作用的，如Java -Dxx=xx -jar app.jar
     * 添加Program Argument参数时使用–，如Java -jar app.jar –xx.xx=xx,并且需要注意的是此类型参数需要放在Java -jar 命令参数的后面 。
     * SpringApplication.run(Application.class,args); 可以try catch ，避免发生错误时看不到异常堆栈
     *
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
        try {
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
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        //继承了org.springframework.context.support.GenericApplicationContext（实现了org.springframework.beans.factory.support.BeanDefinitionRegistry）
        AnnotationConfigServletWebServerApplicationContext con = null;
        DispatcherServlet dispatcherServlet = null;
        //org.springframework.context.annotation.Condition


    }


}

