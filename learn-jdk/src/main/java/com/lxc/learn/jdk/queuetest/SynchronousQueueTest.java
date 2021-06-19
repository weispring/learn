package com.lxc.learn.jdk.queuetest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/17 17:59
 */
@Slf4j
public class SynchronousQueueTest {


    /**
     * SynchronousQueue

     是这样 一种阻塞队列，其中每个 put 必须等待一个 take，反之亦然。
     同步队列没有任何内部容量，甚至连一个队列的容量都没有。
     */
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        new Customer(queue).start();
        new Product(queue).start();
    }

    static class Product extends Thread{
        SynchronousQueue<Integer> queue;
        public Product(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }
        @Override
        public void run(){
            while(true){
                int rand = new Random().nextInt(1000);
                System.out.println("生产了一个产品："+rand);
                System.out.println("等待三秒后运送出去...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.offer(rand);
                System.out.println("产品生成完成："+rand);
            }
        }
    }
    static class Customer extends Thread{
        SynchronousQueue<Integer> queue;
        public Customer(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }
        @Override
        public void run(){
            while(true){
                try {
                    System.out.println("消费了一个产品:"+queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------------------------------");
            }
        }
    }



    @Test
    public void test() throws Exception{
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        synchronousQueue.offer("--");
        System.out.println("");
    }

    @Test
    public void test01() throws InterruptedException {
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        try {
            synchronousQueue.take();
        } catch (Throwable e) {
            throw e;
        }
        System.out.println("");
    }


}
