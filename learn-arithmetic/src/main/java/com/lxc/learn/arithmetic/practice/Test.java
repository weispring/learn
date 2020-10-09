package com.lxc.learn.arithmetic.practice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/9/11
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        int sum = 100;
        //加数个数
        for (int i=100;i>0;--i){

        }
    }

    /**
     * 1 1
     * 2 2
     * 3 3
     * 4 4
     * 5 6
     * 6 5 + 4 + 2 + 3 + 3 + 2 = 19
     * @param n
     * @return
     */
    public static int make(int n){
        int sum = 100;
        int d = sum % n;
        int s =  sum / n;
        if (d ==0 && s <= 1){
            return 1;
        }
        String a = null;
        a.toString();
        return 0;
    }
}
