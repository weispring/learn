package com.lxc.learn.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/19 21:29
 */
@Slf4j
public class FixThreadPoolTest {

    public static class MyTask implements Runnable{

        public void run() {
            System.out.println(System.currentTimeMillis() + "Thread Name:" + Thread.currentThread().getName());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        int size =5;
        //下篇说下阿里技术规范插件对这个的提示问题
//        ExecutorService executorService = new ThreadPoolExecutor(size,size,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
//        ExecutorService executorService2 = new ThreadPoolExecutor(size,size,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);

        ExecutorService es = Executors.newFixedThreadPool(size);
        for (int i = 0; i < 10 ; i++) {
            es.submit(myTask);
        }

        log.info("完成：{}", System.currentTimeMillis());
    }


}
