package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/21 12:28
 */
@Slf4j
public class CyclicBarrierTest {
    /**
     * 实质就是 ReentrantLock Condition 条件锁
     * 1. 客重复使用
     * 2. 相互等待，等待所有线程
     * 3. 强调 同时到达某一个点
     * 4. 独占锁 一个一个的去获取独占锁
     *
     * 最后一个调用 cyclicBarrier.await();线程首先获取锁，然后释放，后续线程获取后也是立马释放
     * 问题
     * 阻塞线程何时唤醒？
     * 1.首先减为0时，调用条件队列的signalAll
     * 2.最后一个调用 cyclicBarrier.await();线程首先获取锁后，释放时会unPark 线程
     * @param args
     */
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        //减为0前，进入条件队列后阻塞，然后进入同步队列唤醒后再去获取锁
        //减为0后，当前线程获取锁，然后signalAll
        // 释放锁后，唤醒下一个线程，一个一个的去获取独占锁，然后马上释放，唤醒下一个
        cyclicBarrier.await();
    }




}
