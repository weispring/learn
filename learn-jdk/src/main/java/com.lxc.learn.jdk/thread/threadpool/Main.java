package com.lxc.learn.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/27
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        testshutdownNow();;
    }

    public static void testshutdownNow(){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,5,300, TimeUnit.SECONDS,new LinkedBlockingQueue<>(100));
        for (int i=0;i<10;i++){
            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("线程id：{}",Thread.currentThread().getId());
                }
            });
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.shutdown();;
    }

}
