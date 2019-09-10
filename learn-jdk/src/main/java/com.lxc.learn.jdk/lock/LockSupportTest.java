package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/10 15:18
 */
@Slf4j
public class LockSupportTest {

    public static void main(String[] args) {
        log.info("主线程开始：{}", Thread.currentThread().getId());

        LockSupport.parkUntil(new LockSupportTest(),System.currentTimeMillis()+10*1000);
        log.info("主线程结束：{}", Thread.currentThread().getId());

        LockSupport.park();
    }

}
