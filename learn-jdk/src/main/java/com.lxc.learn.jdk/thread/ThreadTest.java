package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/12 17:32
 */
@Slf4j
public class ThreadTest {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //返回中断标识
                boolean flag = Thread.currentThread().isInterrupted();
                log.info("flag:{}", flag);
                //中断
                Thread.currentThread().interrupt();
                flag = Thread.currentThread().isInterrupted();
                log.info("flag:{}", flag);
                //返回中断标识，并设置中断标识清除
                flag = Thread.currentThread().interrupted();
                log.info("flag:{}", flag);
                flag = Thread.currentThread().isInterrupted();
                log.info("flag:{}", flag);
            }
        }).start();
    }
}
