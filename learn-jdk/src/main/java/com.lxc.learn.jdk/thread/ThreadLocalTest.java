package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/26 15:46
 */
@Slf4j
public class ThreadLocalTest {

    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    @Test
    public void testThreadLocal() throws InterruptedException {
        threadLocal.set("test");
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("线程变量:{}",threadLocal.get());
            }
        }).start();
        Thread.sleep(3*1000);
        threadLocal.remove();
    }



}
