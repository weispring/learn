package com.lxc.learn.jdk.thread.threadpool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/20 9:34
 */
@Slf4j
public class Test {

    /**
     * 延迟队列的排序
     * 1. 入队时进行一定程序的排序
     * 2. 出对时还要对后面的元素排序
     */

    Object[] arr = new Object[10];


    @Setter
    @Getter
    @AllArgsConstructor
    public static class Dog implements Comparable{

        private int id;

        @Override
        public int compareTo(Object o) {
            return this.id > ((Dog)o).getId() ? 1 : (this.id < ((Dog)o).getId() ? -1 : 0);
        }
    }


    /**
     * 此排序结果不是严格有序，大致有序
     */
    @org.junit.Test
    public void test(){
        Random random = new Random(100);
        for (int i=0;i<10;i++){
            int k = i;
            Dog x = new Dog(random.nextInt(100));
            Comparable key = (Comparable) x;
            while (k > 0) {
                /**
                 * TODO WARN 没有全部覆盖到，替换为 int parent = (k - 1);
                 */
                int parent = (k - 1) >>> 1;
                Object e = arr[parent];
                if (key.compareTo(e) >= 0)
                    break;
                arr[k] = e;
                k = parent;
            }
            arr[k] = key;
        }

        log.info("{}", arr);
    }


}
