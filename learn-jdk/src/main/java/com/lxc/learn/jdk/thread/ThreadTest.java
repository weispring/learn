package com.lxc.learn.jdk.thread;

import jdk.nashorn.internal.codegen.CompilerConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/12 17:32
 */
@Slf4j
public class ThreadTest {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //返回中断标识
                boolean flag = Thread.currentThread().isInterrupted();
                log.info("flag:{}", flag);
                //中断
                Thread.currentThread().interrupt();
                flag = Thread.currentThread().isInterrupted();
                log.info("flag:{}", flag);
                //返回中断标识，并设置中断标识清除
                flag = Thread.currentThread().interrupted();
                log.info("flag:{}", flag);
                flag = Thread.currentThread().isInterrupted();
                log.info("flag:{}", flag);
            }
        }).start();
    }

    @Test
    public void future() throws Exception{
        Callable<String> callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return "123";
            }
        };
        FutureTask<String> ft = new FutureTask<String>(callable);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }


}
