package com.lxc.learn.arithmetic.practice.cpu;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 在Linux下，CPU利用率分为用户态，系统态和空闲态，分别表示CPU处于用户态执行的时间，系统内核执行的时间，
 * 和空闲系统进程执行的时间，三者之和就是CPU的总时间，当没有用户进程、系统进程等需要执行的时候，
 * CPU就执行系统缺省的空闲进程。从平常的思维方式理解的话，CPU的利用率就是非空闲进程占用时间的比例，
 * 即CPU执行非空闲进程的时间/ CPU总的执行时间。
 *
 * 2.获得当前cpu占用率，不断提高时间比例和线程数量
 * @author lixianchun
 * @description
 * @date 2020/6/9
 */
@Slf4j
public class CpuTest {

    private static final Long CPU_CLOCk_SPEED = 36L * 1024 * 1024 * 102;

    private static final Integer CPU_SIZE = Runtime.getRuntime().availableProcessors();

    private static Long COMMAND_COUNT = CPU_CLOCk_SPEED * 2 / 5 / 1000;

    private static Long COUNT = CPU_CLOCk_SPEED * 2 / 5 / 1000 * 8;

    private volatile static Long COUNT_SIN_BASE = CPU_CLOCk_SPEED * 2 / 5 / 1000;

    public static class CustomThread extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                for (long i=0;i<COUNT;i++){
                    int a = 0;
                    //log.info("{}");
                }
                //Thread.sleep(2);
            }
        }
    }
    public static class CustomThread2 extends Thread{
        @SneakyThrows
        @Override
        public void run() {
        /*    for (;;){
                for (int i =1; i <COUNT / 8;i ++);
                Thread.sleep(1);
            }*/

            //采用周期 1000ms,采样间隔10ms
            int interval = 5;
            double t = 1000;
            double pi = Math.PI;

            for (;;){
                for (int i=0;i<t/interval;i++){
                    Long time = System.currentTimeMillis();
                    double busy = interval * Math.sin((System.currentTimeMillis() - time) / t * Math.PI );
                    for (; System.currentTimeMillis() - time < busy;System.out.println(""));
                    Thread.sleep(Math.round(interval - busy));
                }
            }

        }
    }

    public static class CustomThread3 extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            Long s = System.currentTimeMillis();
            for (; ; ) {
                long c = getSin(s);
                for (int i = 1; i < c; i++) ;
                Thread.sleep((COMMAND_COUNT * 2 - c )/2);
            }
        }
    }

    public static class CustomThread4 extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            for (; ; ) {
                for (int i = 1; i < COUNT_SIN_BASE * 10; i++) ;
                Thread.sleep((COMMAND_COUNT  - COUNT_SIN_BASE) * 10 / COMMAND_COUNT);
            }
        }
    }

        public static class TimeThread extends Thread {
            @SneakyThrows
            @Override
            public void run() {
                long timeStar;
                int fulltime = 100;
                int runtime = 50;
                while (true) {
                    System.out.print("-");
            /*    timeStar = System.currentTimeMillis();
                while((System.currentTimeMillis()-timeStar)<runtime){
                    System.out.print("-");
                    //System.err.println( CpuUtil.getCpuRatioForWindows());
                }*/
           /*     try {
                    Thread.sleep(fulltime-runtime);
                }catch (InterruptedException e) {
                    return;
                }*/
                }
            }
        }


        public static void main(String[] args) throws InterruptedException {
            Long time = System.currentTimeMillis();
            for (int i = 0; i < 8; i++) {
                //new CustomThread().start();
                //new TimeThread().start();
                CustomThread3 thread2 = new CustomThread3();
                //thread2.setPriority(Thread.MAX_PRIORITY - i);
                //thread2.setDaemon(true);
                thread2.start();
            }

          /*  double busy = Math.sin(((System.currentTimeMillis() - time) % 1000) * Math.PI / 1000 );
            for (;;){
                COUNT_SIN_BASE = Double.doubleToLongBits(COUNT_SIN_BASE * busy);
                Thread.sleep(10);
            }*/
        }



        public static Long getSin(Long start){
            double d = (System.currentTimeMillis() - start ) % 100 / 100.0d * Math.PI;
            return Math.round(2 * d * COMMAND_COUNT);
        }
    }

