package com.lxc.learn.common.util.test;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author lixianchun
 * @Description
 * @date 2019/7/17 11:15
 */
public class PerformanceUtil {
    public static void main(String[] args) {
        final AtomicInteger seq = new AtomicInteger(0);

        ScheduledExecutorService es = Executors.newScheduledThreadPool(20, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread th = new Thread(r);
                th.setName("consumingthreads-" + seq.incrementAndGet());
                return th;
            }
        });

        for (int i = 0; i < 200; i++) {
            es.scheduleAtFixedRate(new ConsumingCpuTask(), 0, 10, TimeUnit.MILLISECONDS);
        }

        // not terminate the es



        // another thread to print host cpu
        ScheduledExecutorService printer = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread th = new Thread(r);
                th.setName("printer");
                return th;
            }
        });
        // print every 10 seconds
        printer.scheduleAtFixedRate(new PrintCurrentProcessCpuTask(), 0, 10, TimeUnit.SECONDS);
    }

    static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
    // notice here is com.sun.management.OperatingSystemMXBean and it's not java.lang.management.OperatingSystemMXBean
    static final OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * get process cpu in nanoseconds
     */
    static double getProcessCpuTime() {
        return bean.getProcessCpuTime();
    }


    /**
     * A task to simulate consuming cpu
     */
    static class ConsumingCpuTask implements Runnable {

        @Override
        public void run() {
            AtomicInteger integer = new AtomicInteger(0);
            for (int i = 0; i < 10000; i++) {
                integer.incrementAndGet();
            }
        }
    }

    static class PrintCurrentProcessCpuTask implements Runnable {

        double cpuTime = 0;
        long collectTime = 0;

        @Override
        public void run() {
            if (cpuTime == 0) {
                cpuTime = getProcessCpuTime();
                collectTime = System.currentTimeMillis();
            }
            else {
                double newCpuTime = getProcessCpuTime();
                long newCollectTime = System.currentTimeMillis();
                double cpu = (newCpuTime - cpuTime) / (newCollectTime - collectTime) / 1000_000 / PROCESSOR_COUNT;
                cpuTime = newCpuTime;
                collectTime = newCollectTime;
                System.out.println(String.format("Process cpu is: %.2f %%", cpu * 100));
            }
        }
    }

}
