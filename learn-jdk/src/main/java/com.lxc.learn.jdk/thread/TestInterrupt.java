package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/27
 */
@Slf4j
public class TestInterrupt {


    /**
     *     通过调用一个线程的 interrupt() 来中断该线程，如果该线程处于阻塞、限期等待或者无限期等待状态(wait、sleep、join)，那么就会抛出 InterruptedException，从而提前结束该线程。但是不能中断 I/O 阻塞和 synchronized 锁阻塞。
     对于以下代码，在 main() 中启动一个线程之后再中断它，由于线程中调用了 Thread.sleep() 方法，因此会抛出一个 InterruptedException，从而提前结束线程，不执行之后的语句。

     */
    private static class MyThread1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new MyThread1();
        thread1.start();
        thread1.interrupt();
        System.out.println("TestshutdownNow run");
    }


    /**
     *
     *interrupted  返回是否已中断，并重置状态
     */
    @Test
    public void testInterrupt(){
        Integer a = 1;
        boolean result = Thread.interrupted();
        log.info("result:{}",result);
        //设置中断标识为 true
        Thread.currentThread().interrupt();
        //返回是否已中断，并清除中断标识
        result = Thread.interrupted();
        log.info("result:{}",result);

        result = Thread.interrupted();
        log.info("result:{}",result);
    }

    /**
     *    如果一个线程的 run() 方法执行一个无限循环，并且没有执行 sleep() 等会抛出 InterruptedException 的操作，那么调用线程的 interrupt() 方法就无法使线程提前结束。
     但是调用 interrupt() 方法会设置线程的中断标记，此时调用 interrupted() 方法会返回 true。因此可以在循环体中使用 interrupted() 方法来判断线程是否处于中断状态，从而提前结束线程。
     * @return
     */

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                // ..
            }
            System.out.println("Thread end");
        }
    }

    public void test(){
        Thread thread2 = new MyThread2();
        thread2.start();
        thread2.interrupt();
    }

    @Test
    public void te(){
        System.out.println( Integer.toBinaryString(-536870912));
    }

}
