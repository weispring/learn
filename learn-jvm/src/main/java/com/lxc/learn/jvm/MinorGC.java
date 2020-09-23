package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/17
 */
@Slf4j
public class MinorGC {

    private static final int _1MB = 1024*1024;
    /**
     *VM参数：-verbose：gc-Xms20M-Xmx20M-Xmn10M-XX：+PrintGCDetails
     -XX：SurvivorRatio=8
     */
    public static void testAllocation(){
        byte[]allocation1,allocation2,allocation3,allocation4;
        allocation1=new byte[2*_1MB];
        allocation2=new byte[2*_1MB];
        allocation3=new byte[2*_1MB];
        allocation4=new byte[4*_1MB];//出现一次Minor GC
    }

    //-XX:+PrintGCDetails -XX:-UseAdaptiveSizePolicy  -XX:+PrintGCTimeStamps -Xms20M -Xmx20M
    public static void main(String[] args) {
        testAllocation();
    }
    
}
