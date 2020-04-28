package com.lxc.learn.jdk.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/28
 */
@Slf4j
public class QueueTest {

    /**
     * 拥有两把锁，takeLock 和putLock ，读头部，写尾部，互不影响
     */
    public void testLinkedBlockingQueue(){
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
    }

    /**
     * 一把锁
     */
    public void testArrayBlockingQueue(){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(100,true);
    }


}
