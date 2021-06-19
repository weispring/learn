package com.lxc.learn.jdk.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/18
 */
@Slf4j
public class DateTest {

    public static void main(String[] args) {
        String dateStr = "12-MAR-18,15:04:39";//英式日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy,HH:mm:ss", Locale.ENGLISH);
        String d = sdf.format(new Date());
        System.out.println(d);
    }

}
