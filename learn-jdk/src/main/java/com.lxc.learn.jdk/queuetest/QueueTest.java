package com.lxc.learn.jdk.queuetest;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveTask;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/28
 */
@Slf4j
public class QueueTest {

    /**
     * 队列必须加锁码？
     * 是的，因为先进先出，且只能出一次
     *
     * 拥有两把锁，takeLock 和putLock ，读头部，写尾部，互不影响
     */
    public void testLinkedBlockingQueue(){
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
    }

    /**
     * 一把锁，读写共用，并发低
     */
    public void testArrayBlockingQueue(){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(100,true);
    }


}
