package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/10 15:18
 */
@Slf4j
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
   /*     log.info("主线程开始：{}", Thread.currentThread().getId());
        Thread thread = Thread.currentThread();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(6*1000);
                    LockSupport.unpark(thread);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        LockSupport.parkUntil(new LockSupportTest(),System.currentTimeMillis()+10*1000);

        log.info("主线程结束：{}", Thread.currentThread().getId());*/
      interrupts();
    }


    /**
     * 可中断
     */
    @Test
    public void test(){
        //1.先调用unpark，再调用park无效
        //2.下面情况阻塞失效
        // 调用方法   UNSAFE.park(false, nanos);UNSAFE.park(true, nanos); 发生下面情况，阻塞失效
        //             * <li>Some other thread invokes {@link #unpark unpark} with the
        //     * current thread as the target; or
        //                *
        //     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
        //     * the current thread; or
        //                *
        //     * <li>The specified waiting time elapses; or
        //                *
        //     * <li>The call spuriously (that is, for no reason) returns.

        log.info("start");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.parkUntil(System.currentTimeMillis() + 3000);

        LockSupport.park();

        LockSupport.parkNanos(1000);

        ReentrantLock lock = null;
        //lock.tryLock(1,TimeUnit.SECONDS);
        log.info("end");
        //lock.lock();
    }


    /**
     * 若通过@Test测试，则不论是否中断thread.interrupt()，都没有阻塞
     * @throws InterruptedException
     */
    public static void interrupts() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("start");
                //LockSupport.park(); 没有指定时间，需要unpark才能唤醒
                LockSupport.parkUntil(System.currentTimeMillis() + 10 * 1000);
                log.info("end");
            }
        });
        thread.start();
        Thread.sleep(2000);
        //thread.interrupt();
        log.info("main end");
    }
}
