package com.lxc.learn.jdk.cpu;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/22
 */
@Slf4j
public class CpuHighExample {

    public static void cpuHigh() {
        /**
         * top
         * top -Hp pid 查看进程中的线程
         * jstack tid 查看线程快照
         */
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 1;
                while (true) {
                    // 构造thread1为消耗很高cpu的线程
                    count = count + 1;
                }
            }
        });
        thread1.setName("thread1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // 构造thread2为消耗很低cpu的线程
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread2.setName("thread2");
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        cpuHigh();
    }
}
