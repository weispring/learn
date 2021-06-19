package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/11
 */
@Slf4j
public class DateTest {

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = format.parse("2020-06-02T00:00:00");
            log.info("{}",date);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
    }
}
