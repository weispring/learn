package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/22 14:58
 */
@Slf4j
public class KillThread {


    /**
     * 线程中断方法
     * 1.利用中断标识
     * 2.利用InterruptedException
     * 3.定义标识变量
     * 4.stop(已废弃)
     */
    public static class MyThread4 extends Thread {
        @Override
        public void run() {
            super.run();
            for (int i = 0; true; i++) {
                /*if (this.isInterrupted()) {
                    System.out.println( "线程已经结束，我要退出" );
                    break;
                }*/
                log.info("{}",123456 );
                if (i > 100000){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //1.线程调用sleep方法
                        //2.线程中断标志为true,同时满足两个条件(不分先后)，就会抛出InterruptedException，此时中断标志变为false
                        e.printStackTrace();
                    }
                }
                System.out.println("中断标识："+ Thread.currentThread().isInterrupted());
            }
            //System.out.println( "我是for下面的语句，我被执行说明线程没有真正结束" );
        }
    }

    public static void main(String[] args) {
        try {
            MyThread4 myThread4 = new MyThread4();
            myThread4.start();
            Thread.sleep( 1);
            myThread4.interrupt();
            System.out.println("中断标识："+myThread4.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
