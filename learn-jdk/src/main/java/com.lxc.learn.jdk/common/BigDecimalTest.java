package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.DoubleConsumer;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/1
 */
@Slf4j
public class BigDecimalTest {

    public static void main(String[] args) {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);


        /**The results of this constructor can be somewhat unpredictable.
         * The {@code String} constructor, on the other hand, is
         * perfectly predictable:
         */
        //可能会精度丢失，因为传入的double可能无法精确表示
        BigDecimal a = new BigDecimal(1.0001);
        BigDecimal b = new BigDecimal(1);
        BigDecimal c = new BigDecimal("1.01");
        BigDecimal d = new BigDecimal("1.02");
        System.out.println(a.add(b));
        System.out.println(c.add(d));


       System.out.println(Double.toString(22.33333234));
    }

    /**
     * float 有效位数 24
     * double 有效位数 53
     * 当整数超出这个长度，或者是小数部分无法全部表示，便会丢失精度
     */
    @Test
    public void test(){
        double d = 20014999;
        long l = Double.doubleToLongBits(d);
        System.out.println(Long.toBinaryString(l));
        float f = 20014999;
        int i = Float.floatToIntBits(f);
        System.out.println(Integer.toBinaryString(i));
        System.out.println(d);
        System.out.println(f);
        System.out.println(Integer.toBinaryString(20014999));
    }
}
