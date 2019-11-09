package com.lxc.learn.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:44
 * @Description:
 */
@SpringBootApplication//(scanBasePackages = {"com.lxc.learn"})
//@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@Slf4j
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        context.getBeanFactory().getBeanNamesIterator();
    }
}
