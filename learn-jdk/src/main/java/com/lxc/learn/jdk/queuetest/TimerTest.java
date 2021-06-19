package com.lxc.learn.jdk.queuetest;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/20 17:42
 */
@Slf4j
public class TimerTest {


    /**
     * 实现原理
     * 采用了 延迟队列的设计思想
     * 核心方法：java.util.TimerThread#mainLoop()
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer threadId:{}" +Thread.currentThread().getId());
            }
        };
        timer.scheduleAtFixedRate(task, 500,  1000 );
    }
}
