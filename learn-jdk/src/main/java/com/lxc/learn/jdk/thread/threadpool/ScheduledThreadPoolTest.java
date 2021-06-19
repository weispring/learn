package com.lxc.learn.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/19 21:34
 */
@Slf4j
public class ScheduledThreadPoolTest {


    /**
     *  调度线程池
     *  java.util.concurrent.ScheduledThreadPoolExecutor.ScheduledFutureTask#run
     *  每次执行任务时，加入队列且如果线程数小于配置的数量，会新建
     *
     *  1.用到了 延迟队列
     *  java.util.concurrent.ScheduledExecutorService#scheduleAtFixedRate(java.lang.Runnable, long, long, java.util.concurrent.TimeUnit)
     *  创建线程，线程执行任务，获取任务继续执行，获取为空或者异常，则终止线程，若是异常会创建一个新的线程
     *
     * @param args
     */
    public static void main(String[] args) {
        //固定线程数量调度线程池
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        //单个线程调度线程池
        Executors.newSingleThreadScheduledExecutor();
        //创建一个可缓存线程池
        Executors.newCachedThreadPool();
        /**
         * 1,executorService.scheduleAtFixedRate:创建一个周期性任务，从上个任务开始，过period周期执行下一个（如果执行时间>period，则以执行时间为周期）
         * 2,executorService.scheduleWithFixedDelay：创建一个周期上午，从上个任务结束，过period周期执行下一个。
         */
        //如果前边任务没有完成则调度也不会启动

        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    log.info("当前时间：{}" , System.currentTimeMillis() / 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    // shutdown shutdownNow

    @Test //测试有问题 TODO ERROR
    public void testshutdownNow(){
  /*      ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,5,300,TimeUnit.SECONDS,new LinkedBlockingQueue<>(100));

        for (int i=0;i<10;i++){
            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("线程id：{}",Thread.currentThread().getId());
                }
            });
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.shutdown();;*/
    }

}
