package com.lxc.learn.jdk.lock;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程A等待获取lock锁");
                synchronized (lock) {
                    //不用if,用while,防止中断和伪唤醒
                    while(!condition) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //do something
                    try {
                        System.out.println("线程A获取了lock锁");
                        Thread.sleep(1000);
                        System.out.println("线程A将要运行lock.wait()方法进行等待");
                        System.out.println("线程A等待结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程B等待获取lock锁");
                synchronized (lock) {
                    System.out.println("线程B获取了lock锁");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程B将要运行lock.notify()方法进行通知");
                    lock.notify();
                }
            }
        }).start();
    }

}
