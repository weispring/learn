
package com.lxc.learn.doc.test.thread;

import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用两个线程计算1到100累加。
 * @Description
 * @Date 2020-11-23
 * @Created by lixianchun
 */
@Slf4j
public class TwoThreadCalculate {

    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition conditionA = reentrantLock.newCondition();
    private static Condition conditionB = reentrantLock.newCondition();

    private static int i = 1;

    private static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new CalculateRunnable()).start();
        new Thread(new CalculateRunnable()).start();
        new Thread(new CalculateRunnable()).start();

        Thread.sleep(3 * 1000);
        System.out.println(sum);
    }

    public static class CalculateRunnable implements Runnable{

        @Override
        public void run() {
            while (i < 101){
                try{
                    reentrantLock.lock();
                    if (i > 100){
                        reentrantLock.unlock();
                        return;
                    }
                    sum = sum + i;
                    i = i + 1;
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                }finally {
                    reentrantLock.unlock();
                }
            }
        }
    }

}
