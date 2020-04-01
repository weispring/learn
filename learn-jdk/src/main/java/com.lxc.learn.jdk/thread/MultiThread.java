package com.lxc.learn.jdk.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/20 15:48
 * @Description:
 */
public class MultiThread {
    public static void main(String[] args) {
        processID();
        ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("65280："+Long.toBinaryString(65280));

        Long a = Long.parseLong("011111111111111111111111111111111",2);

        System.out.println(a/3600/1000/24/365);

        System.out.println("当前时间："+Long.toBinaryString(System.currentTimeMillis()));

        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.YEAR,100);

        System.out.println("100年后："+Long.toBinaryString(rightNow.getTime().getTime()));

        System.out.println("100年后："+Long.toBinaryString(rightNow.getTime().getTime()).length());

        Date date = new Date(1288834974657L);
        System.out.println(date);
        System.out.println(Long.toBinaryString(1288834974657L));

        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }


    public static String processID()
    {
        /*
        java.lang.management.RuntimeMXBean是Java 虚拟机的运行时系统的管理接口。
        使用它可以获取正在运行的 Java 虚拟机等信息，包括获取PID。

         * runtimeMXBean.getName()取得的值包括两个部分：PID和hostname，两者用@连接。
         */
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String name = runtimeMXBean.getName();
        System.out.println("ManagementFactory.getRuntimeMXBean().getName() : " + name);
        return name.split("@")[0];
    }
}
