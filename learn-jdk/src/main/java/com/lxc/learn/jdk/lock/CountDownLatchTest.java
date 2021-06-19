package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/21 12:15
 */
@Slf4j
public class CountDownLatchTest {
    /**
     * CountDownLatch 是一种被初始化已占有指定数量的共享锁。
     * 一旦锁释放，所有线程都会获取到锁。
     *
     * 1. 一个（或多个线程）等待n个线程执行完毕，侧重等待,一个线程获取锁后立马唤醒下一个线程去获取共享锁，所以不用释放。
     * 2. 可做计数器使用
     * 3. await 获取共享锁
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);
        //尝试获取锁
        countDownLatch.await();
        //释放一个共享锁
        countDownLatch.countDown();

    }
}
