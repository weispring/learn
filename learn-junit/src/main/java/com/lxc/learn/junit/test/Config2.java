package com.lxc.learn.junit.test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@Slf4j
//@Component
//@EnableConfigurationProperties(ConfigProperty.class)
@Getter
@Setter
public class Config2 {

    @Value("${config.prefix.userName}")
    private String userName;


    @Value("${config.prefix.test}")
    private String test;

    @Override
    public String toString() {
        log.info("this.userName:{},this.test:{}",this.userName,this.test);
        return super.toString();
    }

    //@Async
    @PostConstruct
    public void test(){
        //SimpleAsyncTaskExecutor
        this.toString();
    }

    public static void main(String[] args) {
        byte[] bytes = "&".getBytes();
        for (byte b : bytes){
            System.out.println(b);
        }
    }
}
