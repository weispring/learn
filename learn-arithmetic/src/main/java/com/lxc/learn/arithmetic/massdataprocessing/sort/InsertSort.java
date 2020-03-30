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
        int arr[] = {2,1,5,3,6,4,9,8,7};
        int temp;
        for (int i=1;i<arr.length;i++){
            //待排元素小于有序序列的最后一个元素时，向前插入
            if (arr[i]<arr[i-1]){
                temp = arr[i];
                for (int j=i;j>=0;j--){
                    if (j>0 && arr[j-1]>temp) {
                        arr[j]=arr[j-1];
                    }else {
                        arr[j]=temp;
                        break;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }


}
