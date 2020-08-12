package com.lxc.learn.springcloud.commonconfig.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/6
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //ConfigServicePropertySourceLocator.getRemoteEnvironment()
    }
}
