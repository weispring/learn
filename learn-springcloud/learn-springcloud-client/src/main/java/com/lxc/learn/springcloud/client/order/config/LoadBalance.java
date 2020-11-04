package com.lxc.learn.springcloud.client.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @author lixianchun
 * @description
 * @date 2020/11/3
 */
@Slf4j
@Configuration
public class LoadBalance {

    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
