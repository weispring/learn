package com.lxc.learn.arithmetic.massdataprocessing.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/30
 */
@Slf4j
public class SimpleChooseSort {

    public static void main(String[] args) {
        simpleChoose(SortUtil.array);
    }


    /**
     * 简单选择排序
     * 优于冒泡排序，因为交换次数较少
     * @param a
     */
    public static void simpleChoose(int[] a){
        for (int i=1;i<a.length;i++){
            int min = i;
            for (int j=i+1;j<a.length;j++){
                if (a[min] > a[j]){
                    min = j;
                }
            }
            if (min != i){
                SortUtil.swap(a,min,i);
            }
        }
        SortUtil.print(a);
    }

}
