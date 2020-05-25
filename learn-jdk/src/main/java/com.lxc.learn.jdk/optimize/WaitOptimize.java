package com.lxc.learn.jdk.optimize;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/20
 */
@Slf4j
public class WaitOptimize {

    private Integer count = Integer.MAX_VALUE;

    /**调用方法时传递的参数以及在调用中创建的临时变量都保存在栈（Stack）中，速度快。
     * 其他变量，如静态变量、实例变量等，都在堆（Heap）中创建，速度较慢。
     * */
    @Test
    public void test11() {
        long start = System.currentTimeMillis();
        int a = 0;
        for(int i=0;i<1000000000;i++){
            a++;
        }
        long useTime = System.currentTimeMillis()-start;
        System.out.println("useTime:"+useTime);

    }

    static int aa = 0;
    @Test
    public void test12(){
        long start = System.currentTimeMillis();

        for (int i=0;i<1000000000;i++){
            aa++;
        }
        long useTime = System.currentTimeMillis()-start;
        System.out.println("useTime:"+useTime);
    }


    /**
     * 位运算代替乘除法
     在所有的运算中，位运算是最为高效的。因此，可以尝试使用位运算代替部分算术运算，来提高系统的运行速度。最典型的就是对于整数的乘除运算优化。
     */
    @Test
    public void test21() {

        long start = System.currentTimeMillis();
        int a = 2222;
        for(int i=0;i<1000000000;i++){
            a=a*2;
            a=a/2;
        }
        long useTime = System.currentTimeMillis()-start;
        System.out.println("useTime:"+useTime);
    }

    @Test
    public void test22(){
        long start = System.currentTimeMillis();
        int aa = 2222;
        for (int i=0;i<1000000000;i++){
            aa = aa<<1;
            aa = aa>>1;
        }
        long useTime = System.currentTimeMillis()-start;
        System.out.println("useTime:"+useTime);
    }

    /**
     * 使用Buffer进行I/O操作
     除NIO外，使用Java进行I/O操作有两种基本方式；
     使用基于InpuStream和OutputStream的方式；
     使用Writer和Reader;

     无论使用哪种方式进行文件I/O，如果能合理地使用缓冲，就能有效地提高I/O的性能。
     InputStream、OutputStream、Writer和Reader配套使用的缓冲组件。
     */
}
