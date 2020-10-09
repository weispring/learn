package com.lxc.learn.arithmetic.practice.cpu;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 背景
 * 最简单的要求：编写一个程序，控制CPU的占用率为50%.

 考虑： 任务管理器每一秒钟刷新一次，要在这一秒钟内，使CPU的平均使用时间和空闲时间各占50%. 而不是让CPU使用1秒钟，空闲一秒钟。这样的话任务管理器出现的CPU的使用图像会是忽高忽低。windows的调度时间片为10ms,  所以在编写程序的时候就可以让cpu不停的工作10ms, 然后空闲10ms, 空闲操作通过sleep(10)来实现。这样最后任务管理器在刷新CPU占用率的时候就会得到这个一秒钟内的CPU的平均使用率。显示出50%的水平。

 通过空循环，来让cpu保持繁忙的工作状态，通过sleep使cpu空闲。

 机器指令最接近的是汇编语言。

 空的for 循环，换成汇编语言是大概5句，现在的CPU可以在一个时钟周期执行两个机器指令，相当于执行两个汇编语句。

 考虑到我的电脑的主频是2.0G，所以应该是每秒可以执行  2000000000*2/5=800000000 条空循环。 然而不能让机器执行一秒的空循环，应该分时间片的执行。一个时间片10ms, 所以执行空循环应该为8000000次。

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
