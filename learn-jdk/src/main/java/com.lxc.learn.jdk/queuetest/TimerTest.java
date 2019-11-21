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
     * 实现原理 延迟队列
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
