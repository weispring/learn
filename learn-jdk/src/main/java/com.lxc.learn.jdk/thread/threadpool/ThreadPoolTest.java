package com.lxc.learn.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/17 16:25
 */
@Slf4j
public class ThreadPoolTest {

    private static AtomicInteger number = new AtomicInteger();
    private static AtomicInteger fail = new AtomicInteger();

    /**
     * AbortPolicy	该策略会直接抛出异常，阻止系统正常 工作。线程池默认为此。
     CallerRunsPolicy	只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务。
     DiscardOledestPolicy	该策略将丢弃最老的一个请求，也就是即将被执行的一个任务，并尝试重新提交当前任务。
     DiscardPolicy	该策略默默地丢弃无法处理的任务，不予任务处理。

     */

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queues = new LinkedBlockingQueue<>(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, queues, new RejectedExecutionHandler() {


            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //丢弃前，先执行
                r.run();
                fail.incrementAndGet();
                System.err.println("run thread-id:{},orderId:{}"+ Thread.currentThread().getId()+"{}"+((Task)r).getOrderId());
            }
        });
        //核心线程不超时销毁
        threadPoolExecutor.allowCoreThreadTimeOut(false);

        for (int i=0;i<10;i++){
            threadPoolExecutor.execute(new Task(number.incrementAndGet()));
        }
        while ((threadPoolExecutor.getCompletedTaskCount() + fail.get()) != 10){
            System.out.println(threadPoolExecutor.getCompletedTaskCount() + fail.get());
            //Thread.sleep(1*1000);
        }
    }


    public static class Task implements Runnable{

        public Task(Integer id){
            orderId = id;
        }

        private Integer orderId;

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
          /*  try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            log.info("run thread-id:{},orderId:{}", Thread.currentThread().getId(),orderId);
        }
    }


<<<<<<< HEAD


=======
    /**
     * AbortPolicy	该策略会直接抛出异常，阻止系统正常 工作。线程池默认为此。
     CallerRunsPolicy	只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务。
     DiscardOledestPolicy	该策略将丢弃最老的一个请求，也就是即将被执行的一个任务，并尝试重新提交当前任务。
     DiscardPolicy	该策略默默地丢弃无法处理的任务，不予任务处理。

     * @throws Exception
     */


>>>>>>> 55df2b1... async to sync 、 threadPool、cache




}
