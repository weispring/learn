package com.lxc.learn.junit.control.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/2
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class SseDemo {

    @GetMapping("/sseDemo")
    public SseEmitter sseDemo() throws InterruptedException, IOException {
        final SseEmitter emitter = new SseEmitter(0L); //timeout设置为0表示不超时
        ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(1,5,60,TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000));
        taskExecutor.execute(() -> {
            try {

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        for(int i=0;i<100;i++){
            emitter.send("hello"+i);
            log.info("emit:{}","hello"+i);
            Thread.sleep(1000*1);
        }
        emitter.complete();

        log.error("已完成");
        return emitter;
    }
}
