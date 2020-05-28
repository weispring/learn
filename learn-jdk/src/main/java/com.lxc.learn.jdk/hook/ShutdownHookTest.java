package com.lxc.learn.jdk.hook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/13
 */
@Slf4j
public class ShutdownHookTest {


    /**
     * If the virtual machine aborts
     * then no guarantee can be made about whether or not any shutdown hooks
     * will be run.
     */
    public static void main(String[] args) throws Exception{
        log.info("main start");
        Thread.currentThread().sleep(2000);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("jvm exit");
                throw new RuntimeException("----------------");
                //
            }
        });
        log.info("main end");
    }

}
