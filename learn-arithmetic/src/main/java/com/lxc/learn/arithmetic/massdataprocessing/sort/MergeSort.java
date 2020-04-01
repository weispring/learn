package com.lxc.learn.arithmetic.massdataprocessing.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/1
 */
@Slf4j
public class MergeSort {

    private static int count = 1;

    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2, 9, 10};
        int[] temp = new int[arr.length];
        split(arr, 0, arr.length - 1, temp);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 递归拆分数组然后合并
     *
     * @param arr   待拆分数组
     * @param left  数组左边下标
     * @param right 数组右下标
     * @param temp  用于存放合并后的有序序列的数组
     */
    public static void split(int[] arr, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }
        System.out.println("拆分第"+(count++)+"次");
        int mid = left + (right - left) / 2;
        //向左拆分
        split(arr, left, mid, temp);
        //向右拆分
        split(arr, mid + 1, right, temp);
        //每次拆分后都执行合并
        merge(arr, left, mid, right, temp);
    }

    /**
     * 合并两个各自有序序列(以mid为界)
     *
     * @param arr   原始数组
     * @param left  数组左边下标
     * @param mid   数组中间下标
     * @param right 数组右边下标
     * @param temp  用于存放新的有序数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        //temp中的原始下标
        int t = 0;

        while (i <= mid && j <= right) {
            //两边数组都没有比较完 继续
            if (arr[i] < arr[j]) {
                //左边数组中值更小
                temp[t] = arr[i];
                i++;
            } else {
                //右边数组中值更小
                temp[t] = arr[j];
                j++;
            }
            t++;
        }
        //有一边已经全部复制到temp中了
        if (i <= mid) {
            //左边还没有复制完，将左边全部元素复制到temp中
            while (i <= mid) {
                temp[t] = arr[i];
                i++;
                t++;
            }
        } else if (j <= right) {
            //右边还没有复制完，将右边全部元素复制到temp中
            while (j <= right) {
                temp[t] = arr[j];
                j++;
                t++;
            }
        }
        //将temp复制到原arr中
        t = 0;
        while (left <= right) {
            arr[left] = temp[t];
            left++;
            t++;
        }
    }
}

