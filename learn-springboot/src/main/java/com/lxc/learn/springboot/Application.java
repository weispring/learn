package com.lxc.learn.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:11
 */
@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DataSourceHealthIndicatorAutoConfiguration.class},
                        scanBasePackages = {"com.lxc.learn.springboot.bean.configurebeforeandafter"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
