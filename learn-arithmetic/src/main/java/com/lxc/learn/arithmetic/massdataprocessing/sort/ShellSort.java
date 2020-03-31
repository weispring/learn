package com.lxc.learn.arithmetic.massdataprocessing.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/31
 */
@Slf4j
public class ShellSort {

    public static void main(String[] args) {
        shellSort(SortUtil.array);
    }


    /**
     * 希尔排序 时间复杂度 n的二分之三次方
     * @param a
     */
    public static void shellSort(int[] a){
        int increntment = a.length;
        do {
            increntment = increntment / 3 + 1;
            for (int i = increntment + 1;i<a.length;i++){
                if (a[i] < a[i-increntment]){
                    a[0] = a[i];
                    int j;
                    for (j=i-increntment;j>0 && a[j] > a[0];j=j-increntment){
                        a[j+increntment] = a[j];
                    }
                    a[j+increntment] = a[0];
                }
            }
        }while (increntment > 1);

        System.out.println(Arrays.toString(a));
    }

}
