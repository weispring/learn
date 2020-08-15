package com.lxc.learn.springcloud.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/6
 */
@Slf4j
@SpringBootApplication
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
