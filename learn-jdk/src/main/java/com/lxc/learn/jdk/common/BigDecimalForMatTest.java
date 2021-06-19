package com.lxc.learn.jdk.common;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/18
 **/
@Slf4j
public class BigDecimalForMatTest {

    /**
     * 0	显示数字，如果位数不够则补 0
     * #	显示数字，如果位数不够不显示
     * .	小数分隔符
     * -	减号
     * ,	组分隔符
     * E	分隔科学记数法中的尾数和小数
     * %	前缀或后缀，乘以 100 后作为百分比显示
     * ?	乘以 1000 后作为千进制货币符显示。用货币符号代替。如果双写，用国际货币符号代替；
     * 如果出现在一个模式中，用货币十进制分隔符代替十进制分隔符
     */
    @Test
    public void test(){
        List list = new ArrayList(10);
        list.add(new BigDecimal("4"));
        list.add(new BigDecimal("4.3"));
        list.add(new BigDecimal("0.3"));
        list.add(new BigDecimal("12345.1234"));

        List<DecimalFormat> formats = new ArrayList(6);
        formats.add(new DecimalFormat(",##0?", new DecimalFormatSymbols(Locale.CHINA)));
        //formats.add(new DecimalFormat(",##0.00", new DecimalFormatSymbols(Locale.CHINA)));
        for (DecimalFormat format : formats){
            for (Object o : list){
                log.info("source: {}, format: {} ", o.toString(), format.format(o));
            }
        }

        log.info("123,456,3.002".replaceAll("[1-9]", "#"));
    }
}
