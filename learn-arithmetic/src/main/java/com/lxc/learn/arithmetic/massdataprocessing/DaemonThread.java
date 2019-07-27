package com.lxc.learn.arithmetic.massdataprocessing;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/27 10:21
 */
@Slf4j
public class DaemonThread {

    public static void main(String[] args) {
        log.info("主线程开始:{}",System.currentTimeMillis());

        Thread thread1= new DaemonThread().createThread("用户", 1000);
        Thread thread2= new DaemonThread().createThread("用户2", 5000);
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allThread();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allThread();

        log.info("主线程结束:{}",System.currentTimeMillis());
    }


    public Thread createThread(String threadName, long sleep){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("开始{}线程：{}", threadName, Thread.currentThread().getId());
                daemonThread(sleep);
                try {
                    Thread.currentThread().sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.gc();
                log.info("结束{}线程：{}", threadName, Thread.currentThread().getId());
            }
        });
        thread.start();
        return thread;
    }

    public Thread daemonThread(long sleep){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               /* log.info("开始守护线程：{}", Thread.currentThread().getId());
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("结束守护线程：{}", Thread.currentThread().getId());*/

               while (true){
                   log.info("守护线程：{}", Thread.currentThread().getId());
               }

            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    public static void allThread(){
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        //System.out.println("Thread list size == " + list.length);
        for (Thread thread : list) {
            System.out.println(thread.getId() + ":" + thread.getName());
        }
    }


}
