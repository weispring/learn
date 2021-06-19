package com.lxc.learn.jdk.customtimeout;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: lixianchun
 * @Date: 2020/5/22 20:38
 * @Description:
 */
@Slf4j
public class ClientService {

    /**
     * 超时机制三种实现方式
     * 1.同步方式，一个线程，即执行又及时
     * 2.对于执行会导致阻塞的，只能靠异步方式实现，新起线程执行，本线程计时
     * Callable
     * object.wait(timeout)
     * https://blog.csdn.net/fangquan1980/article/details/52460578
     */
    public static void main(String[] args) throws InterruptedException {
        int timeout = 30;
        ThirdResp thirdResp = new ThirdResp();
        new ThirdService(thirdResp,Thread.currentThread()).start();
        System.out.print("---------11");
        LockSupport.parkUntil(System.currentTimeMillis() + timeout * 1000);
        System.out.print("---------22");
        //Thread.(timeout * 1000);
        if (thirdResp.getCode() == null){
            log.error("调用超时：{}");
        }

    }

}
