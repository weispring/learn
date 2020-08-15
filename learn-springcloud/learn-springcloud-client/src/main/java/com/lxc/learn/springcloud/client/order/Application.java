package com.lxc.learn.springcloud.client.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/6
 */
@SpringBootApplication(scanBasePackages = {"com.lxc.learn.springcloud"},exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = {"com.lxc.learn.springcloud"}
)
@EnableHystrix
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
