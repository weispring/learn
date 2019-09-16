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
public class ReentrantLockConditionOrderTest {

    /**
     * 3.充分发掘Condition的灵活性，可以用它来实现顺序执行线程。
     */
    // 通过nextThread控制下一个执行的线程
    private static int nextThread = 1;
    private ReentrantLock lock = new ReentrantLock();
    // 有三个线程，所以注册三个Condition
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    public void excuteA() {
        try {
            lock.lock();
            while (nextThread != 1) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + " 工作");
            nextThread = 2;
            conditionB.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void excuteB() {
        try {
            lock.lock();
            while (nextThread != 2) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + " 工作");
            nextThread = 3;
            conditionC.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void excuteC() {
        try {
            lock.lock();
            while (nextThread != 3) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + " 工作");
            nextThread = 1;
            conditionA.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private static Runnable getThreadA(final ReentrantLockConditionOrderTest service) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    service.excuteA();
                }
            }
        };
    }

    private static Runnable getThreadB(final ReentrantLockConditionOrderTest service) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    service.excuteB();
                }
            }
        };
    }

    private static Runnable getThreadC(final ReentrantLockConditionOrderTest service) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    service.excuteC();
                }
            }
        };
    }

    public static void main(String[] args) throws InterruptedException{
        ReentrantLockConditionOrderTest service = new ReentrantLockConditionOrderTest();
        Runnable A = getThreadA(service);
        Runnable B = getThreadB(service);
        Runnable C = getThreadC(service);

        new Thread(A, "A").start();
        new Thread(B, "B").start();
        new Thread(C, "C").start();
    }

}
