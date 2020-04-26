package com.lxc.learn.jdk.thread;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/14
 */
@Slf4j
public class DaemonThread {

    public static void main(String[] args) {
        new MainThread().start();
        System.out.println("exit");

        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-");
                }
            }
        }.start();
    }


    public static class MainThread extends Thread{

        @Override
        public void run() {

            AtomicInteger complete = new AtomicInteger(0);
            Thread t = new DaemonMainThread(complete);
            t.setDaemon(true);
            t.start();

            int i = 0;
            while (i < 5){
                log.info("MainThread 线程id：{}",Thread.currentThread().getId());
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            complete.set(1);

        }
    }

    /**
     * 夫线程死亡后，自身也死亡
     */
    @Getter
    @Setter
    public static class DaemonMainThread extends Thread{

        private AtomicInteger complete;

        public DaemonMainThread(AtomicInteger complete){
            this.complete = complete;
        }

        @Override
        public void run() {
            //守护线程退出条件
            while (true && complete.get() == 0){
                log.error("线程id：{},{}",Thread.currentThread().getId(),complete);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
