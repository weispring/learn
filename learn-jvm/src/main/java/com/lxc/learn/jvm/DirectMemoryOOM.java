package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/17
 */
@Slf4j
/**
 *VM Args：-Xmx20M-XX：MaxDirectMemorySize = 10M
 *@author zzm
 */
public class DirectMemoryOOM{
    private static final int _1MB = 1024*1024;
    public static void main(String[]args)throws Exception{
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        AtomicInteger i = new AtomicInteger();
        while(true){
            unsafe.allocateMemory(_1MB);
            log.info("{}",i.incrementAndGet());
        }
    }
}
