package com.lxc.learn.springcloud.commonconfig.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/6
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
        //ConfigServicePropertySourceLocator.getRemoteEnvironment()
        //DiscoveryClient
    }
}

/** 参考文献
 *  https://www.cnblogs.com/fengzheng/p/11242128.html
 *  ConfigServicePropertySourceLocator
    PropertySourceBootstrapConfiguration
 *
 * 刷新远程配置变量的改动至当前服务
 * http://localhost:3302/actuator/refresh
 */