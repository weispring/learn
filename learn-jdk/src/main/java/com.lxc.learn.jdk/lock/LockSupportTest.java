package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/10 15:18
 */
@Slf4j
public class LockSupportTest {

    public static void main(String[] args) {
        log.info("主线程开始：{}", Thread.currentThread().getId());
        LockSupport.parkUntil(new LockSupportTest(),System.currentTimeMillis()+10*1000);
        log.info("主线程结束：{}", Thread.currentThread().getId());
    }


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

        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();

        LockSupport.parkUntil(100);
        LockSupport.parkNanos(100000);


    }

}
