package com.lxc.learn.jdk.customtimeout;

/**
 * @Auther: lixianchun
 * @Date: 2020/5/22 20:58
 * @Description:
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 此法思路貌似狠好，却无法实现
 *
 * 本线程设置了一个超时时间
 * 该线程开始运行后，经过指定超时时间，
 * 该线程会抛出一个未检查异常通知调用该线程的程序超时
 * 在超时结束前可以调用该类的cancel方法取消计时
 * @author solonote
 */
@Slf4j
public class TimeoutThread extends Thread{
    /**
     * 计时器超时时间
     */
    private long timeout;
    /**
     * 计时是否被取消
     */
    private boolean isCanceled = false;
    /**
     * 当计时器超时时抛出的异常
     */
    private TimeoutException timeoutException;

    private volatile boolean timeOut;

    private Thread thread;
    /**
     * 构造器
     * @param timeout 指定超时的时间
     */
    public TimeoutThread(long timeout,TimeoutException timeoutErr,Thread thread) {
        super();
        this.timeout = timeout;
        this.timeoutException = timeoutErr;
        this.thread = thread;

        //设置本线程为守护线程
        this.setDaemon(true);
    }

    /**
     * 取消计时
     */
    public synchronized void cancel(){
        isCanceled = true;
    }
    /**
     * 启动超时计时器
     */
    public void run() {
        try {
            thread.interrupt();
            System.out.print(" =====" + thread.isInterrupted());
            Thread.sleep(timeout);
            if(!isCanceled){
                timeOut = true;
                thread.sleep(1);
                log.warn("抛出超时异常");
                throw timeoutException;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    /**
     * 注: 类一中的TimeoutException是下边的用户自定义类，
     * 不是java中的java.util.concurrent.TimeoutException
     * 类2.抛出异常类,该类继承了RuntimeException，原因是run方法不能抛出已检测异常。
     */

    public static class TimeoutException extends RuntimeException {
        /**
         * 序列化号
         */
        private static final long serialVersionUID = -8078853655388692688L;

        public TimeoutException(String errMessage)
        {
            super(errMessage);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        TimeoutThread t = new TimeoutThread(3000,new TimeoutThread.TimeoutException("超时"),Thread.currentThread());

        try{
            t.start();
            //要检测超时的程序段
            boolean flag = true;
            while (flag){

            }

            t.cancel();
        }catch (TimeoutThread.TimeoutException e) {
            System.out.print("--------");
            log.error(e.getMessage(),e);
        }
        System.out.print("000000000");
    }
}


