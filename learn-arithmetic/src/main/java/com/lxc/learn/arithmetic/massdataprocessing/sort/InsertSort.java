package com.lxc.learn.arithmetic.massdataprocessing.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/30
 */
@Slf4j
public class InsertSort {

    public static void main(String[] args) {
        int arr[] = {2,1,0,3,6,4,9,8,7,11,33,22,99,88,87,25};

    }


    /**
     * 插入排序，较于简单选择排序和冒泡排序，比较和交换次数较少，
     * 当序列本身有序的时候，效果更佳
     * @param arr
     */
    public static void insertSort(int[] arr){
        int temp;
        for (int i=1;i<arr.length;i++){
            //待排元素小于有序序列的最后一个元素时，向前插入
            if (arr[i]<arr[i-1]){
                // temp 为需要插入的数据
                temp = arr[i];
                for (int j=i;j>=0;j--){
                    if (j>0 && arr[j-1]>temp) {
                        //有序序列比插入值大的话，不断后移
                        arr[j]=arr[j-1];
                    }else {
                        // 插入数据
                        arr[j]=temp;
                        break;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
