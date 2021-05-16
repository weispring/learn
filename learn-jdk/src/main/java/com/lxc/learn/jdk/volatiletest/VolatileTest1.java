package com.lxc.learn.jdk.volatiletest;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/5/16
 **/
public class VolatileTest1 {
    public static void main(String[] args) throws InterruptedException {
        Aobing a = new Aobing();
        a.start();
        for (; ;){
            /**
             * 因为某一个线程进入synchronized代码块前后，线程会获得锁，清空工作内存，从主内存拷贝共享变量最新的值到工作内存成为副本，执行代码，将修改后的副本的值刷新回主内存中，线程释放锁。
             * 而获取不到锁的线程会阻塞等待，所以变量的值肯定一直都是最新的。
             */
            synchronized (a){
                if (a.isflag()){
                    System.out.println("有点东西");
                }
            }
        }
    }

    static class Aobing extends Thread {
        private boolean flag = false;

        public boolean isflag(){
            return flag;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            flag = true;
            System.out.println("flag = " + flag);
        }
    }
}
