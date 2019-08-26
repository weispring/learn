package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/23 16:10
 */
@Slf4j
public class MapTest {

    public static void main(String[] args) {
        //初始容量 为2的n次幂，n为initialCapacity的二进制高位第一位为1至最低位的位数
        Map map = new HashMap(16);
        Map map1 = new HashMap(15);
        map1.put("1", "1");
        Map map2 = new HashMap(17);
        map1.put("1", "1");
        map.put("key001", "value");
        map.put("key001", "value1");
        map.put("key002", "value2");
        map.put("key003", "value3");

        int c = (16 -1)  & 17;

        int a = (16 -1)  & 17;

        log.info("{}", 1);

    }

    public static void setBitLight(int cap){
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        log.info("{} >>>{}", cap,n);
        log.info("{} >>>{}", Integer.toBinaryString(cap),Integer.toBinaryString(n));
    }
}
