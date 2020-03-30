package com.lxc.learn.arithmetic.massdataprocessing.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/30
 */
@Slf4j
public class SortUtil {

    public static int[] array = {0,1,33,4,5,2,88,44,7,5,3,55};

    private static ThreadLocal<AtomicInteger> atomicIntegerThreadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return new AtomicInteger(0);
        }
    };

    public static void swap(int[] a,int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        atomicIntegerThreadLocal.get().getAndAdd(1);
        System.out.println(atomicIntegerThreadLocal.get());
    }


    public static void print(int[] a){
        for (int ele : a){
            System.out.print(ele + " ");
        }
    }

}
