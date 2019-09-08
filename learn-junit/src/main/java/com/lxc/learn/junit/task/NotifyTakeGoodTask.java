package com.lxc.learn.junit.task;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/19 18:13
 */
@Component
@ConditionalOnProperty(
        prefix = "task",
        name = {"switch"},
        havingValue = "true",
        matchIfMissing = true
)
@Slf4j
public class NotifyTakeGoodTask {

    /**
     * 1. @Async 需要@EnableAsync 执行一次会创建一个新的线程
     * 2. 不加异步、配置线程池均无法解决 执行时间大于执行间隔的情况
     * @throws InterruptedException
     */
    //@Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void notifyTake() throws InterruptedException {
        //可以考虑在过滤器中,加入我们需要的信息，然后打印到日志中
        MDC.put("traceId", "LXC0214");
        Thread.currentThread().sleep(1000*5);
        log.info("{}:取货,threadName:{},threadId:{}",System.currentTimeMillis(),Thread.currentThread().getName(),Thread.currentThread().getId());

    }


    @Scheduled(cron = "0/5 * * * * ?")
    public void notifyDeliver() throws InterruptedException {

        Thread.currentThread().sleep(1000*5);
        log.error("{}:发货,threadName:{},threadId:{}",System.currentTimeMillis(),Thread.currentThread().getName(),Thread.currentThread().getId());

    }

}
