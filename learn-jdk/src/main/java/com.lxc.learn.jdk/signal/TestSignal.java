package com.lxc.learn.jdk.signal;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Signal;

/**
 * @Auther: lixianchun
 * @Date: 2018/10/10 14:38
 * @Description:
 */
@Slf4j
public class TestSignal {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000*10);
                    Signal diagSignal = new Signal("INT");
                    log.info("signal :{}",diagSignal.getName());
                    Signal.raise(diagSignal);
                    log.info("signal :{}",diagSignal.getName());
                }catch (Exception e){

                }

            }
        }).start();
    }
}
