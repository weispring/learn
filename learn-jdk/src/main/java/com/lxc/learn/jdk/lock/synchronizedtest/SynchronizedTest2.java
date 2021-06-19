package com.lxc.learn.jdk.lock.synchronizedtest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/27 17:53
 */
@Slf4j
public class SynchronizedTest2 {
    private volatile int number = 0;

    /**
     * As in the one argument version, interrupts and spurious wakeups are possible, and this method should always be used in a loop:
     * 在一个参数版本中，中断和虚假的唤醒是可能的，这个方法应该总是在循环中使用:
     * if 转换伟 while
     *
     * 意见：正常唤醒无法保证线程顺序，不是什么虚假唤醒
     */

    public synchronized void increment() throws InterruptedException {
//        判断
        if (number!=0){
            this.wait();
        }
//        干活
        ++number;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
//        通知唤醒
        this.notify();
    }

    public synchronized void decrement() throws InterruptedException {
        if (number==0){
            this.wait();
        }
        --number;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notify();
    }


    public static void main(String[] args) {
        SynchronizedTest2 sd = new SynchronizedTest2();

        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    sd.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    sd.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A1").start();

        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    sd.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    sd.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B1").start();
    }
}