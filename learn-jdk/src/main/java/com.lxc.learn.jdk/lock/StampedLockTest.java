package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.StampedLock;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/17 12:08
 */
@Slf4j
public class StampedLockTest {

    /**
     * 1. 读写锁已经能够出来 写饥饿的问题，这个的意义在于什么？
     *
     * @param args
     */
    public static void main(String[] args) {
        if (null != null){
            log.info("{}", "aaa");
        }
        StampedLock lock = new StampedLock();
        long write = lock.writeLock();
        long read = lock.readLock();

        lock.unlockRead(read);
        lock.validate(1);



        lock.tryOptimisticRead();

        lock.writeLock();
        lock.unlockWrite(1);

        String a = "00";
        a += "  " +  99;



        System.out.println(a);
        byte[] bytes = new byte[1024];
        test(1L,bytes);
    }

    public static void test(long a,byte[] bytes){
        if (a > Long.MAX_VALUE){
            a = 0;
        }
        else {
            test(a,bytes);
        }
    }
}
