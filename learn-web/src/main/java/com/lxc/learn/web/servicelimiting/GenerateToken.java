package com.lxc.learn.web.servicelimiting;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/30 14:23
 */
@Slf4j
public class GenerateToken {

    /**
     * 1.计算器算法
     * 2.令牌桶算法
     * 3.漏桶算法（如：消息队列）
     */
    //每秒处理的请求数
    private static Integer requests = 5;

    public static Queue TOKEN_QUEUE = new LinkedBlockingDeque(requests);

    private static AtomicLong lastRequstTime = new AtomicLong();

    public static Long getLastRequstTime() {
        return lastRequstTime.get();
    }

    public static void setLastRequstTime(Long value) {
        GenerateToken.lastRequstTime.set(value);
    }

    static {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    try{
                        if (!TOKEN_QUEUE.add(new Object())){

                        }
                    }catch (Exception e){
                        //log.error(e.getMessage(), e);
                        try {
                            Thread.currentThread().sleep(1000/requests*10);
                        } catch (InterruptedException e1) {
                            log.error(e1.getMessage(), e1);
                        }
                    }

                    try {
                        Thread.currentThread().sleep(1000/requests);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
