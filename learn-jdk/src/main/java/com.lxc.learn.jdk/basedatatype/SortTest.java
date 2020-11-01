package com.lxc.learn.jdk.basedatatype;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/26 14:19
 */
@Slf4j
public class SortTest {

    /**
     * 冒泡排序
     * @param args
     */
    public static void main(String[] args) {
        Integer[] nums = new Integer[]{1,345,4,8,99,22,66,100,0};
        for (int i=0;i<nums.length;i++){
            for (int j=i+1;j<nums.length;j++){
                if (nums[i] > nums[j]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
            log.info("{}", nums[i]);
        }
        for (Integer a : nums){
            log.info("{}", a);
        }
    }

}
