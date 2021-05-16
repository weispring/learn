package com.lxc.learn.jdk;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/5/15
 **/
public class Test1 {

    private volatile Long x;

    private volatile Long x1 = 1L, x2 = 1L, x3 = 1L, x4 = 1L, x5 = 1L, x6 = 1L, x7 = 1L, x8 = 1L, x9 = 1L, x10 = 1L, x11 = 1L, x12 = 0L;

    private static Test1[] array = new Test1[2];
    static {
        array[0] = new Test1();
        array[1] = new Test1();
    }

    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        int count = 10000;
        //
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < count; i++)
                    array[0].x = Long.valueOf(i);
                System.out.println("=====");
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < count; i++)
                    array[0].x = Long.valueOf(i);
                System.out.println("=====");
            }
        };
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();

        System.out.println(": " + (System.currentTimeMillis() - start));
    }
}
