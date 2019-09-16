package com.lxc.learn.jdk.lock;

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
                boolean flag = Thread.currentThread().interrupted();
                log.info("flag:{}", flag);
            }
        }).start();




    }
}
