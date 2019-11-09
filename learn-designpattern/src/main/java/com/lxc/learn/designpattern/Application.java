package com.lxc.learn.designpattern;

import com.lxc.learn.common.util.web.ListBeans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 17:14
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.lxc.learn"})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        ListBeans.list(context);
    }
}
