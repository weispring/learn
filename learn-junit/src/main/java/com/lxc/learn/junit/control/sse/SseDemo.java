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

/**
 * @author lixianchun
 * @description
 * @date 2020/4/2
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class SseDemo {

    ThreadPoolTaskExecutor mvcTaskExecutor = new ThreadPoolTaskExecutor();

    @GetMapping("/sseDemo")
    public SseEmitter sseDemo() throws InterruptedException {
        final SseEmitter emitter = new SseEmitter(0L); //timeout设置为0表示不超时
        mvcTaskExecutor.execute(() -> {
            try {
                for(int i=0;i<100;i++){
                    emitter.send("hello"+i);
                    log.info("emit:{}","hello"+i);
                    Thread.sleep(1000*1);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        log.error("已完成");
        return emitter;
    }
}
