package com.lxc.learn.junit.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/27 11:48
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "custom")
//@EnableConfigurationProperties
public class CustomerConfig {
    private String testVarilable;
    private String task;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void postconstruct(){
        System.out.print(environment.getProperty("spring.application.name"));
        System.out.print(environment.getProperty("spring.application.name"));
    }
}
