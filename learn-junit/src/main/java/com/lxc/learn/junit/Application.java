package com.lxc.learn.junit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:44
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.lxc.learn"})
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@Slf4j
//读取当前项目的其他yml配置，其依赖的jar好像不能读取
//@org.springframework.context.annotation.PropertySource({"classpath:config-common.yml","classpath:config-web.yml"})
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
        while (iterator.hasNext()){
            log.error("----{}----", iterator.next());
        }

        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
        //System.exit(0);
    }
}
