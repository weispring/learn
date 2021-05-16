package com.lxc.learn.jdk.volatiletest;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/5/16
 **/
public class VolatileTest2 {
    public static void main(String[] args) throws InterruptedException {
        Aobing a = new Aobing();
        a.start();
        for (; ;){
            if (a.isflag()){
                System.out.println("有点东西");
            }
        }
    }

    static class Aobing extends Thread {
        private volatile boolean flag = false;

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
