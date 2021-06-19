package com.lxc.learn.jdk.lock.synchronizedtest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/27 18:10
 */
@Slf4j
public class SynchronizedTest {


    /**
     * synchronized
     * 1. 不能被中断
     */

    private Integer integer = 100;

    @Test
    public void test(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                synchronized (integer){
                    try {
                        log.info("阻塞");
                        integer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("结束");
                }
            }
        };
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(){
            @Override
            public void run() {
                synchronized (integer){
                    LockSupport.unpark(thread);
                    try {
                        Thread.sleep(10*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    integer.notify();
                }
            }
        }.start();

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("主线程结束");
    }

}
