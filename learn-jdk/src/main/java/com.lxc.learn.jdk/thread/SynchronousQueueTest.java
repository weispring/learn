package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;

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

}
