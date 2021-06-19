package com.lxc.learn.jdk.enums;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/7
 */
@Getter
@Slf4j
public enum TestEnum {

    AA;

    @Setter
    private String name;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
              while (true){
                  AA.setName("a");
                  Thread.currentThread().sleep(1000);
                  log.info("线程：{}，name：{}",Thread.currentThread().getId(),AA.getName());
              }
            }
        }).start();


        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    AA.setName("b");
                    Thread.currentThread().sleep(3000);
                    log.info("线程：{}，name：{}",Thread.currentThread().getId(),AA.getName());
                }
            }
        }).start();
    }
}
