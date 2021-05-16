package com.lxc.learn.jdk.common;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
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


    /**
     * 集合需要进行查找时，选择set而不是list
     */
    @Test
    public void test(){
        List list = new ArrayList(4);
        if (list.contains("a")){
            //需要循环遍历，所以慢些
        }

        Set set = new HashSet(16);
        if (set.contains("a")){
            //hash查找更快
        }
    }

    /**
     * @see #test 变式一
     * @see com.lxc.learn.jdk.common.SetIterator#test 变式一
     * 双层循环查找相等
     */
    @Test
    public void test1(){
        String a = (String)null;
        Collection collection = new ArrayList(10);
        List list = new ArrayList(10);

        for (Object o : collection){
            for (Object obj : list){
                if (obj != null && obj.equals(o)){
                    //
                }
            }
        }
        //改为
        Set set = new HashSet(16);
        for (Object o : collection){
            if (set.contains(o)){
                //
            }
        }
        //System.out.println(); 禁止使用，因为他是一个同步方法，影响性能
    }

}
