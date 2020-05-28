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
     * 限流的分类
     合法性验证限流：比如验证码、IP 黑名单等，这些手段可以有效的防止恶意攻击和爬虫采集；
     容器限流：比如 Tomcat、Nginx 等限流手段，其中 Tomcat 可以设置最大线程数（maxThreads），当并发超过最大线程数会排队等待执行；而 Nginx 提供了两种限流手段：一是控制速率，二是控制并发连接数；
     服务端限流：比如我们在服务器端通过限流算法实现限流，如下:
     * 1.计算器算法(时间窗口算法)
     * 2.令牌桶算法
     * 3.漏桶算法（如：消息队列）,此法最好
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
