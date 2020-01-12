package com.lxc.learn.remind.util;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 13:50
 * @Description:
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class DateUtil {
    public static String BIRTHDAY = "MMdd";
    public static String FORMATDAY = "yyyyMMdd";
    public static String FORMATDAY_MM = "yyyyMMddHHmm";
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMATDAY_SS = "yyyyMMddHHmmss";
    public static String FORMAT_MM = "yyyy-MM-dd HH:mm";
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public DateUtil() {
    }

    public static String birthDay() {
        SimpleDateFormat formatter = new SimpleDateFormat(BIRTHDAY);
        return formatter.format(new Date());
    }

    public static String getYmd() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATDAY);
        return formatter.format(new Date());
    }

    public static Date format(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATDAY);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getYmd(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATDAY);
        return formatter.format(date);
    }

    public static Date getNextWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, 7);
        return cd.getTime();
    }

    public static Date getNextMonth(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, 1);
        return cd.getTime();
    }


    public static Date getNextYear(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.YEAR, 1);
        return cd.getTime();
    }



    public static Date getDayBegin() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        String beginStr = dateStr + " 00:00:00";
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_LONG);
        try{
            return formatter.parse(beginStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDayEnd() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        String endStr = dateStr + " 23:59:59";
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_LONG);
        try{
            return formatter.parse(endStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }





    public static String formatTime(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static Date year(int year) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(1, year);
        return Cal.getTime();
    }

    public static Date year(Date date, int year) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(1, year);
        return Cal.getTime();
    }

    public static Date month(int month) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(2, month);
        return Cal.getTime();
    }

    public static Date month(Date date, int month) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(2, month);
        return Cal.getTime();
    }

    public static Date day(int day) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(6, day);
        return Cal.getTime();
    }

    public static Date day(Date date, int day) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(6, day);
        return Cal.getTime();
    }

    public static Date hour(float hour) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(12, (int)(hour * 60.0F));
        return Cal.getTime();
    }

    public static Date hour(Date date, float hour) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(12, (int)(hour * 60.0F));
        return Cal.getTime();
    }

    public static Date minute(int minute) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(12, minute);
        return Cal.getTime();
    }

    public static Date minute(Date date, int minute) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(12, minute);
        return Cal.getTime();
    }
}

