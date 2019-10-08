package com;

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
        if (o == p ) {
            log.info("opq:{}", true);
        }
        String r = String.valueOf("r");
    }
}
