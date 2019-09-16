package com.lxc.learn.jdk.basedatatype;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: lixianchun
 * @Date: 2019/9/14 22:06
 * @Description:
 */
@Slf4j
public class AtomicTest {
    public static void main(String[] args) {
        //原子类的数字类型加减运算都是通过自旋锁实现
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.addAndGet(1);
    }
}
