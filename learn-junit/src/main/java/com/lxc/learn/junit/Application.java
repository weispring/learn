package com.lxc.learn.junit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:44
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.lxc.learn"})
//@EnableScheduling
@EnableAsync
@Slf4j
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        context.getBeanFactory().getBeanNamesIterator();
    }
}
