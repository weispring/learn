package com.lxc.learn.jdk.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/1
 */
@Slf4j
public class TestDeadLock {

    private static Integer Locka = new Integer(666);

    private static Integer Lockb = new Integer(888);

    public static class ThreadA extends Thread{
        private Integer locka;
        private Integer lockb;

        public ThreadA(Integer a, Integer b){
            this.locka = a;
            this.lockb = b;
        }

        @SneakyThrows
        @Override
        public void run() {
            synchronized (locka){
                log.info("{}-{}",this.getClass().getName(),Thread.currentThread().getId());
                Thread.sleep(10);
                synchronized (lockb){
                    log.info("{}-{}",this.getClass().getName(),Thread.currentThread().getId());
                }
            }
        }
    }


    public static class ThreadB extends Thread{
        private Integer locka;
        private Integer lockb;

        public ThreadB(Integer a, Integer b){
            this.locka = a;
            this.lockb = b;
        }

        @SneakyThrows
        @Override
        public void run() {
            synchronized (lockb){
                log.info("{}-{}",this.getClass().getName(),Thread.currentThread().getId());
                Thread.sleep(10);
                synchronized (locka){
                    log.info("{}-{}",this.getClass().getName(),Thread.currentThread().getId());
                }
            }
        }
    }

    /**
     * 检测死锁 1.jstack -l pid or jconsole 工具界面
     * @param args
     */
    public static void main(String[] args) {
        log.info("{},{}",Locka.hashCode(),Lockb.hashCode());
        new ThreadA(Locka,Lockb).start();
        new ThreadB(Locka,Lockb).start();
    }

}
