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

    public static void main(String[] args) {
/*
        StampedLock lock = null;

        lock.validate(1);

        lock.tryOptimisticRead();

        lock.writeLock();
        lock.unlockWrite(1);*/

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
