package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/12 9:09
 */
@Slf4j
public class TestArrayList {

    /**
     * Error ,线程不安全
     */
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (int i=0;i<10;i++){
                        list.add(String.valueOf(i));
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (String s : list){
                        System.out.println(s);
                    }
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Thread.sleep(3600*1000);
    }

}
