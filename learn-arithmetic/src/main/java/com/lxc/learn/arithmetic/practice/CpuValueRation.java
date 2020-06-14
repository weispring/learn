package com.lxc.learn.arithmetic.practice;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/11
 */
@Slf4j
public class CpuValueRation {

    private static final Long CPU_CLOCk_SPEED = 36L * 1024 * 1024 * 102;

    //逻辑核
    private static final Integer CPU_SIZE = Runtime.getRuntime().availableProcessors();

    private static final Long COMMAND_COUNT_PER_SECOND = CPU_CLOCk_SPEED * 2 / 5 / 1000;



    public static class CustomThread extends Thread{

        public CustomThread(double ratio){
            //减去本身消耗
            this.ratio = ratio - 0.2d;
        }

        private double ratio;
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                for (long i=0;i<COMMAND_COUNT_PER_SECOND * 100 * ratio;i++){

                }
                Thread.sleep(Math.round(100 * (1-ratio)));
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Long time = System.currentTimeMillis();
        for (int i = 0; i < CPU_SIZE; i++) {
            new CustomThread(0.7d).start();
        }
    }



}
