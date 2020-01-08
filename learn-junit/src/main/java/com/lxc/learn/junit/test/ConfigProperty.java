package com.lxc.learn.junit.test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@Slf4j
@ConditionalOnProperty(prefix = "config",name = "switch",value = "true",matchIfMissing = false)
//@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config.prefix")
@Getter
@Setter
public class ConfigProperty {

    private String userName;

    private String password;

    @Override
    public String toString() {
        log.info("this.userName:{},this.password:{}",this.userName,this.password);
        return super.toString();
    }

    @PostConstruct
    public void test(){
        this.toString();
    }
}
