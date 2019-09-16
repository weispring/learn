package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/11 21:16
 */
@Slf4j
public class ReentrantLockTest {

    private static ReentrantLock lock = new ReentrantLock();

    private static int a = 0;

    public static void main(String[] args) {
        final int[] num = {0};
        for (int i=0;i<10;i++){
            new Thread(){
                @Override
                public void run() {
                    lock.lock();
                    num[0] = num[0]+1;
                    a++;
                    lock.unlock();
                }
            }.start();
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("结果：{}", num[0]);
        log.info("a结果：{}", a);

    }

}
