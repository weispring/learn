package com.lxc.learn.jdk.common;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/28
 */
@Slf4j
public class SetIterator {

    //set线程不安全，但是同一个set可以多线程迭代（前提set 不允许被修改），因为每次产生新的迭代器new iterator

    static Set<String> set = new HashSet<>();

    static {
        for (int i=0;i<10;i++){
            set.add(String.valueOf(i));
            log.info("{}");
        }
        //CopyOnWriteArraySet底层采用了CopyOnWriteArrayList数据结构来实现。在add元素时，采用的是可重入锁来实现线程安全。
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        copyOnWriteArraySet.add(new Object());
    }

    public static void main(String[] args) {
        Iterator iterator = set.iterator();
        for (int i=0;i<10;i++){
            new SubThread(iterator).start();
        }
    }


    public static class SubThread extends Thread{

        private Set set;

        Iterator iterator;

        public SubThread(Iterator iterator){
            this.iterator = iterator;
        }

        @Override
        public void run() {
            while (iterator.hasNext()){
                log.info("{},{},{}",iterator.toString(),Thread.currentThread().getId(),iterator.next());
            }
        }
    }

}
