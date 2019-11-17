package com.lxc.learn.jdk.thread;

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

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queues = new LinkedBlockingQueue<>(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, queues, new RejectedExecutionHandler() {


            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
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


    @Test
    public void test(){
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        synchronousQueue.offer("--");
        synchronousQueue.offer("-33-");
        System.out.println("");
    }
}
