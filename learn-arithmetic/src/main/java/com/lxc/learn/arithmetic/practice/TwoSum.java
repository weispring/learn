package com.lxc.learn.arithmetic.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/8
 */
@Slf4j
public class TwoSum {

    /**
     * 问题内容是：给定一个数组，给定一个数字。返回数组中可以相加得到指定数字的两个索引。

     比如：给定nums = [2, 7, 11, 15], target = 9
     那么要返回 [0, 1]，因为2 + 7 = 9
     这道题的优解是，一次遍历+HashMap：
     */

    public static void main(String[] args) {
        Integer[] nums = new Integer[]{2, 7, 11, 15};
        int[] a = {2, 7, 11, 15};
        System.out.println(twoSum(a,9));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
