package com.lxc.learn.jdk.lock.condition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: lixianchun
 * @Date: 2019/9/14 21:56
 * @Description:
 */
@Slf4j
public class ReentrantLockConditionTest {

    /**
     * Condition 维护一个等待队列,相比较与ReentrantLock，多维护了一个等待队列
     * 1.使用Condition来实现等待/唤醒，并且能够唤醒指定线程。
     * 2.生产者和消费者
     * 3.充分发掘Condition的灵活性，可以用它来实现顺序执行线程。
     */

    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        Runnable runnable1 = new MyServiceThread1(service);
        Runnable runnable2 = new MyServiceThread2(service);

        new Thread(runnable1, "a").start();
        new Thread(runnable2, "b").start();

        // 线程sleep2秒钟
        Thread.sleep(2000);
        // 唤醒所有持有conditionA的线程
        service.signallA();


        Thread.sleep(2000);
        // 唤醒所有持有conditionB的线程
        service.signallB();
    }


    public static class MyService {

        // 实例化一个ReentrantLock对象
        private ReentrantLock lock = new ReentrantLock();
        // 为线程A注册一个Condition
        public Condition conditionA = lock.newCondition();
        // 为线程B注册一个Condition
        public Condition conditionB = lock.newCondition();

        public void awaitA() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "进入了awaitA方法");
                long timeBefore = System.currentTimeMillis();
                // 执行conditionA等待
                //进入等待队列，释放锁，线程被阻塞，
                // 从等待队列移除，加入同步队列，后面被唤醒，获取锁
                conditionA.await();
                long timeAfter = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+"被唤醒");
                System.out.println(Thread.currentThread().getName() + "等待了: " + (timeAfter - timeBefore)/1000+"s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void awaitB() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "进入了awaitB方法");
                long timeBefore = System.currentTimeMillis();
                // 执行conditionB等待
                conditionB.await();
                long timeAfter = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+"被唤醒");
                System.out.println(Thread.currentThread().getName() + "等待了: " + (timeAfter - timeBefore)/1000+"s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signallA() {
            try {
                lock.lock();
                System.out.println("启动唤醒程序");
                // 唤醒所有注册conditionA的线程
                conditionA.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void signallB() {
            try {
                lock.lock();
                System.out.println("启动唤醒程序");
                // 唤醒所有注册conditionB的线程
                conditionB.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }


    public static class MyServiceThread1 implements Runnable {

        private MyService service;

        public MyServiceThread1(MyService service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.awaitA();
        }
    }

    public static class MyServiceThread2 implements Runnable{

        private MyService service;

        public MyServiceThread2(MyService service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.awaitB();
        }
    }
}
