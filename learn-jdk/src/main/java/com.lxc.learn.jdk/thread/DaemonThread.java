package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;

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
    }


    public static class MainThread extends Thread{

        @Override
        public void run() {
            Thread t = new DaemonMainThread();
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

        }
    }

    /**
     * 夫线程死亡后，自身也死亡
     */
    public static class DaemonMainThread extends Thread{

        @Override
        public void run() {
            while (true){
                log.error("线程id：{}",Thread.currentThread().getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
