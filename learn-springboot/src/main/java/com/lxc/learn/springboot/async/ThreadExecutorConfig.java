package com.lxc.learn.springboot.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@Slf4j
@Configuration
public class ThreadExecutorConfig {

    private Integer cpuNum = Runtime.getRuntime().availableProcessors();

    private BlockingQueue blockingQueue = new LinkedBlockingQueue(1024);



    /**
     * (cpuTimes + ioTimes) / cpuTimes * cpu
     */
    @Bean
    public ThreadPoolExecutor configExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,4*2,60, TimeUnit.SECONDS,blockingQueue);
        return threadPoolExecutor;
    }

}
