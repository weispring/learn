package com.lxc.learn.jdk.volatiletest;

/**
 * https://www.cnblogs.com/cxy2020/p/12951333.html
 * @Description
 * @Author lixianchun
 * @Date 2021/5/16
 **/
public class VolatileTest {
    public static void main(String[] args) throws InterruptedException {
        Aobing a = new Aobing();
        a.start();
        //Thread.sleep(2000);
        for (; ;){
            //读取一次后，后面读取的都是副本
            if (a.isflag()){
                System.out.println("有点东西");
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
