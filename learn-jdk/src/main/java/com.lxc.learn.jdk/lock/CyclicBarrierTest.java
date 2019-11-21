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
     * 4. 独占锁
     * @param args
     */
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        cyclicBarrier.await();
    }
}
