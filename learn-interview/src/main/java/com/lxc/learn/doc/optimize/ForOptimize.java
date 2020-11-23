package com.lxc.learn.doc.optimize;

/**
 * @Description
 * @Date 2020-11-23
 * @Created by lixianchun
 */
public class ForOptimize {
    /**
     * 关于多层for循环迭代的效率优化问题
     * 以下代码时间复杂度
     *
     * (1000+1000*100+10*100*1000)*2+10*100*1000=3202000
     */
    public static void test(){
        for(int i = 0 ; i < 1000 ;i++){
            for(int j = 0; i < 100; j++){
                for(int k = 0;k < 10; k++ ){
                    //输出
                    //fun(i,j,k);
                }
            }
        }
    }

    /**
     * (10+10*100+10*100*1000)*2+10*100*1000=3002020
     * 以小循环驱动大循环性能更佳
     */
    public static void testOptimize(){
        for(int k=0; k < 10; k++){
            for(int j=0; j <100; j++){
                for(int i=0; i <1000; i++){
                    //function(i,j,k);
                }
            }
        }
    }

}
