package com.lxc.learn.jdk.basedatatype;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/22 16:26
 */
@Slf4j
public class NumTest {

    @Test
    public void testByte(){
        int i = Integer.MAX_VALUE - 300;
        //带符号位右移，正数高位补0，负数补1，
        //>>>无符号位右移，高位补0
        //int a = (i >> 31) | (-i >>> 31);
        int m = i >> 3;

        log.info("{}", Integer.toBinaryString(i));
        log.info("{}", Integer.toBinaryString(m));

        //Byte Short Integer Long 均缓存了数据[-128,h],h=127,Integer h可配置
        Integer a = 122;
        Integer b = 122;
        Integer c = Integer.valueOf(122);
        if (a == b && b == c) {
            log.info("a==b:{}", true);
        }

        Short o = 100;
        Short p = 100;
        Short q = Short.valueOf("100");
        if (o == p && p == q) {
            log.info("opq:{}", true);
        }
        String r = String.valueOf("r");
    }


    // -XX:AutoBoxCacheMax=200
    @Test
    //在启动 jvm 的时候，通过 -XX:AutoBoxCacheMax=<size> 来指定这个缓冲池的大小，该选项在 JVM 初始化的时候会设定一个名为 java.lang.IntegerCache.high 系统属性，然后 IntegerCache 初始化的时候就会读取该系统属性来决定上界。
    public void testIntegerCache(){
        Integer a = 129;
        Integer b = 129;

        if (a == b){
            System.out.println("a==b");
        }
    }

    /**
     * 1.1 字面量属于 double 类型
     * 字面量 1 是 int 类型
     */
    public void hiddenConvert(){
        /*float 与 double
        Java 不能隐式执行向下转型，因为这会使得精度降低。
        1.1 字面量属于 double 类型，不能直接将 1.1 直接赋值给 float 变量，因为这是向下转型。*/

        // float f = 1.1;         1.1f 字面量才是 float 类型。
        float f = 1.1f;
   /*
        隐式类型转换
        因为字面量 1 是 int 类型，它比 short 类型精度要高，因此不能隐式地将 int 类型向下转型为 short 类型。*/

        short s1 = 1;
        // s1 = s1 + 1;       但是使用 += 或者 ++ 运算符会执行隐式类型转换。
        s1 += 1;
        s1++;
        //上面的语句相当于将 s1 + 1 的计算结果进行了向下转型：
        s1 = (short) (s1 + 1);

    }

}
