package com.lxc.learn.junit.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
}
