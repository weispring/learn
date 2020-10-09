package com.lxc.learn.vue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Iterator;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:11
 */
@Slf4j
@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
                        scanBasePackages = {"com.lxc.learn.vue"})
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
    }


}

