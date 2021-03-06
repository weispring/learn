package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/13
 */
@Slf4j
public class VectorTest {



    private static CopyOnWriteArrayList<String> vector = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        new Add().start();
        new For().start();
        Thread.currentThread().sleep(100000);
        //依赖 CopyOnWriteArrayList
        CopyOnWriteArraySet copyOnWriteArraySet = null;
    }



    public static class Add extends Thread{

        @Override
        public void run() {
            int i=0;
            while (true){
               vector.add(String.valueOf(++i));
            }
        }
    }

    public static class For extends Thread{
        @Override
        public void run() {
            int i=0;
            while (true){
                for (String s : vector){
                    System.out.println(s);
                }
            }
        }
    }
}
