package com.lxc.learn.jdk.thread.future;

/**
 * @Auther: lixianchun
 * @Date: 2018/10/4 09:14
 * @Description:
 */
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@Slf4j
public class TestFuture {
    private class Thread1 implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(5000);
            log.info("thread1 sleep 5s");
            new RestTemplate().getForObject("http://abc.2314", JSONObject.class);
            return "i am thread one";
        }

    }

    private class Thread2 implements Callable<String>{

        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            log.info("thread2 sleep 2s");

            if (1 == 1) throw new RuntimeException();

            return "i am thread two";
        }

    }

    private class Thread3 implements Callable<String>{

        @Override
        public String call() throws Exception {
            Thread.sleep(8000);
            log.info("thread3 sleep 8s");
//        Thread.currentThread().interrupt();
            return "i am thread three";
        }

    }


    private class MyThreadTest {

        private  ExecutorService threadPool = Executors.newFixedThreadPool(20);

        public void test() {
            ThreadPoolExecutor s = null;
            //s.submit()

            log.info("提交线程1");
            Thread1 thread1 = new Thread1();
            Future<String> futureTask1 = threadPool.submit(thread1);

            log.info("提交线程2");
            Thread2 thread2 = new Thread2();
            Future<String> futureTask2 = threadPool.submit(thread2);

            log.info("提交线程3");
            Thread3 thread3 = new Thread3();
            Future<String> futureTask3 = threadPool.submit(thread3);
            log.info("当前时间： {}",System.currentTimeMillis());

            String string1 = null;
            String string2 = null;
            String string3 = null;
            log.info("当前时间1： {}",System.currentTimeMillis());
            try {
                log.info("当前时间2： {}",System.currentTimeMillis());
                log.info("线程1：{}",string1);
                string1 = futureTask1.get();
                log.info("当前时间3： {}",System.currentTimeMillis());
                log.info("线程1：{}",string1);
            }catch (Exception e) {
                futureTask1.cancel(true);
            }
            try {
                log.info("线程2：{}",string2);
                string2 = futureTask2.get();
                log.info("线程2：{}",string2);
            }catch (Exception e) {
                futureTask2.cancel(true);
            }
            try {
                log.info("线程3：{}",string3);
                string3 = futureTask3.get();
                log.info("线程3：{}",string3);
            }catch (Exception e) {
                futureTask3.cancel(true);
            }

//        Thread.currentThread().wait(1000);
//        futureTask1.cancel(true);
//        futureTask2.cancel(true);
//        futureTask3.cancel(true);

            System.out.println(string1 + ": " + string2 + ": " + string3);
            threadPool.shutdown();
        }
    }

    public static void main(String[] args) {

        TestFuture testFuture = new TestFuture();

        MyThreadTest myThreadTest = testFuture.new MyThreadTest();
        myThreadTest.test();
    }
}
