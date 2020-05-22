package com.lxc.learn.jdk.customtimeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @Auther: lixianchun
 * @Date: 2020/5/22 21:32
 * @Description:
 */
public class ThreadTest {

    private static Executor executor=Executors.newCachedThreadPool();

    public static void main(String[] args) {
        test01();
        //test02();
    }

    /**
     * 线程池，主线程往线程池提交任务（子线程）
     * 如果任务中有往外抛出异常，主线程并不会捕获异常。
     * 是线程池捕获了任务的异常
     */
    public static void test02(){
        ThreadTask aTask=new ThreadTask(1);
        ThreadTask aTask2=new ThreadTask(2);
        ThreadTask aTask3=new ThreadTask(6);
        List<ThreadTask> aList=new ArrayList<ThreadTask>();
        aList.add(aTask);
        aList.add(aTask2);
        aList.add(aTask3);
        try {
            for(ThreadTask a:aList){
                executor.execute(a);
            }
            System.out.println("ThreadTest.test02(没有执行)");
        } catch (Exception e) {
            System.out.println("ThreadTest.test02()"+e);
        }
    }


    /**
     * 如果用匿名线程，匿名线程中有抛出异常，无捕获的话，主线程是会捕获这个异常
     */
    public static  void test01(){
        System.out.println("ThreadTest.test01()==>主线程运行开始");
        final List<String> list=new ArrayList<String>();
        try {
            list.add("sxf");
            list.add("chn");
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out
                            .println("ThreadTest.test01().new Runnable() {...}.run()子线程加爱");
                    list.add("love");
                    System.out
                            .println("ThreadTest.test01().new Runnable() {...}.run()子线程抛出异常");
                    int a=3/0;
                    System.out
                            .println("ThreadTest.test01().new Runnable() {...}.run()z县城结束");
                }
            });
            thread.start();
        } catch (Exception e) {
            System.out.println("ThreadTest.test01()子线程抛出异常"+e);
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(String aString:list){
            System.out.println("ThreadTest.test01()打印==>"+aString);
        }
        System.out.println("ThreadTest.test01()==>主线程运行结束");
    }



    public static class ThreadTask implements Runnable{

        private int a;

        public ThreadTask(int a){
            this.a=a;
        }

        @Override
        public void run() {
            if(a%2==0){
                System.out.println("ThreadTask.run()【"+a+"】运行正常.......");
            }else{
                System.out.println("ThreadTask.run()【"+a+"】抛出异常");
                int s=2/0;
            }
        }
    }

}