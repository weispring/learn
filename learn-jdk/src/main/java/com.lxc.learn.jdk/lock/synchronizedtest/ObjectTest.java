package com.lxc.learn.jdk.lock.synchronizedtest;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: lixianchun
 * @Date: 2019/9/14 19:10
 * @Description:
 */
@Slf4j
public class ObjectTest {

    private static boolean condition = false;

    public static void main(String[] args) {
        Object lock = new Object();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("线程A等待获取lock锁");
                synchronized (lock) {
                    //不用if,用while,防止中断和伪唤醒
                    if (!condition) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //do something
                    try {
                       log.info("线程A获取了lock锁");
                        Thread.sleep(1000);
                        log.info("线程A将要运行lock.wait()方法进行等待");
                        log.info("线程A等待结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("线程B等待获取lock锁");
                thread.interrupt();
                synchronized (lock) {
                    log.info("线程B获取了lock锁");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("线程B将要运行lock.notify()方法进行通知");
                    condition = true;
                    lock.notify();
                }
            }
        }).start();
    }

}
