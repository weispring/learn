package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/17
 */
@Slf4j
public class MaxTenuringThresholdTest {
    private static final int _1MB = 1024*1024;

    @SuppressWarnings("unused")
    public static void testTenuringThreshold(){
        byte[]allocation1,allocation2,allocation3;
        allocation1=new byte[_1MB/4];
        //什么时候进入老年代取决于XX：MaxTenuringThreshold设置
        allocation2=new byte[4*_1MB];
        allocation3=new byte[4*_1MB];
        allocation3=null;
        allocation3=new byte[4*_1MB];
    }

    /**
     *VM参数：-verbose：gc-Xms20M-Xmx20M-Xmn10M-XX：+PrintGCDetails-XX：SurvivorRatio=8-XX：MaxTenuringThreshold=1
     -XX：+PrintTenuringDistribution
     */
    public static void main(String[] args) {
        testTenuringThreshold();
    }
}
