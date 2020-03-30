package com.lxc.learn.arithmetic.massdataprocessing.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/30
 */
@Slf4j
public class BubbleSort {

    public static void main(String[] args) {
        int[] a = {-1,1,33,4,5,2,88,44,7,0,3,55};
        bubbleSort(SortUtil.array);
    }


    /**
     * 非标准冒泡排序
     * @param a
     */
    public static void bubbleSortSwap(int[] a){
        for (int i=1;i<a.length;i++){
            for (int j=i+1;j<a.length;j++){
                if (a[i] > a[j]){
                    SortUtil.swap(a,i,j);
                }
            }
        }
        SortUtil.print(a);
    }


    /**
     * 冒泡排序，加上flag,当序列本身有序时，不用再进行后面的比较了
     * @param a
     */
    public static void bubbleSort(int[] a){
        boolean flag = true;
        for (int i=1;i<a.length && flag ;i++){
            flag = false;
            for (int j = a.length - 2;j >= i ;j--){
                if (a[j] > a[j+1]){
                    SortUtil.swap(a,j,j+1);
                    flag = true;
                }
            }
        }
        SortUtil.print(a);
    }

}
