package com.lxc.learn.springcloud.commonconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/6
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
