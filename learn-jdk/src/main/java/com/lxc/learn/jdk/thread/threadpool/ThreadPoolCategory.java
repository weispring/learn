package com.lxc.learn.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author lixianchun
 * @description
 * @date 2020/11/1
 */
@Slf4j
public class ThreadPoolCategory {

    public static void main(String[] args) {

        /**核心线程数为0，最大线程数Integer.MAX_VALUE，队列SynchronousQueue 创建一个可缓存线程池
         高并发时，可能导致创建大量线程，不适用
         如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。终止后，如果你又提交了新的任务，则线程池重新创建一个工作线程。
         在使用CachedThreadPool时，一定要注意控制任务的数量，否则，由于大量线程同时运行，很有会造成系统OOM。
         */
        Executors.newCachedThreadPool();

        /**
         * newFixedThreadPool
         * 最大线程数nThreads
         * keepAliveTime 0
         * LinkedBlockingQueue
         创建一个指定工作线程数量的线程池。每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。
         FixedThreadPool是一个典型且优秀的线程池，它具有线程池提高程序效率和节省创建线程时所耗的开销的优点。
         但是，在线程池空闲时，即线程池中没有可运行任务时，它不会释放工作线程，还会占用一定的系统资源。
         */
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        /**
         * newSingleThreadExecutor
         *最大线程数 1
         * keepAliveTime 0
         * LinkedBlockingQueue
         创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，它只会用唯一的工作线程来执行任务，
         保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。如果这个线程异常结束，会有另一个取代它，保证顺序执行。
         单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。
         */
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


        /**
         *  newScheduleThreadPool
         super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
         new DelayedWorkQueue());
         创建一个定长的线程池，而且支持定时的以及周期性的任务执行，支持定时及周期性任务执行。
         */
        ExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(5);


        /**
         *创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
         * 线程池中最多执行1个线程，之后提交的线程活动将会排在队列中以此执行并且可定时或者延迟执行线程活动。
         */
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    }

    /**
     * 缓存线程池
     * 只有普通线程，60s后超时
     * SynchronousQueue
     *
     * 普通线程池
     * 0s 后超时
     * LinkedBlockingQueue
     *
     * 调度线程池，通过延时队列实现
     * DelayedWorkQueue
     * 0s 后超时
     */
}
