package com.lxc.learn.jdk.lock;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.concurrent.Semaphore;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/21 15:10
 */
@Slf4j
public class SemaphoreTest {

    /**
     * 允许几个线程执行，指定数量的共享锁,可做限流使用
     * 信号量
     * @param args
     */
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release(1);
        }
    }
}
