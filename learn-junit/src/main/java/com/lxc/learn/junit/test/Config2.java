package com.lxc.learn.junit.test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Getter
@Setter
public class Config2 {

    @Value("${config.prefix.userName1}")
    private String userName;

    @Override
    public String toString() {
        log.info("this.userName:{}",this.userName);
        return super.toString();
    }

    @Async
    @PostConstruct
    public void test(){
        //SimpleAsyncTaskExecutor
        this.toString();
    }
}
