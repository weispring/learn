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
     * 其上的操作在乐观读时，如果有写操作修改了共享变量则升级乐观读为悲观读锁，这样避免乐观读反复的循环等待写锁的释放，避免浪费CPU资源。所以在我们的使用StampedLock的时候，建议这样操作。
     看起来好像StampedLock性能又比ReadWriteLock锁好，那是不是都可以用StampedLock抛弃ReadWriteLock？
     并不是的，StampedLock不是可重入锁，所以不支持重入，并且StampedLock不支持条件变量，也就是没Condition。如果是线程使用writeLock()或者readLock()获得锁之后，线程还没执行完就被interrupt()的话，会导致CPU飙升....坑啊

     //就拿acquireWrite举例，acquireRead也是类似的。
     private long acquireWrite(boolean interruptible, long deadline) {
     WNode node = null, p;
     for (int spins = -1;;) { // spin while enqueuing
     //省略代码无数
     if (interruptible && Thread.interrupted())
     return cancelWaiter(node, node, true);
     }
     }

     复制代码首先里面是个无限循环，然后 if (interruptible && Thread.interrupted())已经得知调用的interruptible参数传入的是false，所以Thread.interrupted()也不会执行到，也一定调用不到cancelWaiter，所以就一直循环循环，CPU使用率就会涨涨涨。

     所以如果要使用中断功能就得用readLockInterruptibly()或者writeLockInterruptibly()来获得锁。
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


    /**
     * 乐观读
     */
    public void testOptimisticRead(){
        StampedLock sl = new StampedLock();
        double x = 0,y = 0;
        // A read-only method
        long stamp = sl.tryOptimisticRead(); //乐观读
        double currentX = x, currentY = y;
        if (!sl.validate(stamp)) { //判断共享变量是否已经被其他线程写过
            stamp = sl.readLock();  //如果被写过则升级为悲观读锁
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp); //释放悲观读锁
            }
        }
        Math.sqrt(currentX * currentX + currentY * currentY);
    }


    /**
     * 升级为写锁
     */
    public void moveIfAtOrigin() { // upgrade
        StampedLock sl = new StampedLock();
        double x = 0,y = 0;
        // Could instead start with optimistic, not read mode
        long stamp = sl.readLock(); //获取读锁
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);  //升级为写锁
                if (ws != 0L) {
                    stamp = ws;
                    x = 2;
                    y = 4;
                    break;
                }
                else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                    x = 2;
                    y = 4;
                }
            }
        } finally {
            //异常情况可能是 释放读锁
            sl.unlock(stamp);
        }
    }





}
