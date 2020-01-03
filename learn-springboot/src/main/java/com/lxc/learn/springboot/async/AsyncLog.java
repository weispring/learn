package com.lxc.learn.springboot.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@Slf4j
@Component
public class AsyncLog {

    /**
     * 默认使用线程池
     * org.springframework.core.task.SimpleAsyncTaskExecutor
     */
    @Async(value = "configExecutor")
    public void printLog(Long time){
        try {
            Thread.currentThread().sleep(1000);
            log.info("保存日志：{},{},{}",Thread.currentThread().getThreadGroup().getName(),Thread.currentThread().getName(),Thread.currentThread().getId());
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
    }
}
