package com.lxc.learn.common.util;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 15:44
 */

@Slf4j
public final class DateUtil {
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
    private static final String DATETIME_FORMAT;
    private static final String DATE_FORMAT;
    private static final String TIME_FORMAT;
    private static ThreadLocal<SimpleDateFormat> ThreadDateTime;
    private static ThreadLocal<SimpleDateFormat> ThreadDate;
    private static ThreadLocal<SimpleDateFormat> ThreadTime;

    public DateUtil() {
    }

    private static SimpleDateFormat DateTimeInstance() {
        SimpleDateFormat df = (SimpleDateFormat)ThreadDateTime.get();
        if (df == null) {
            df = new SimpleDateFormat(DATETIME_FORMAT);
            ThreadDateTime.set(df);
        }

        return df;
    }

    private static SimpleDateFormat DateInstance() {
        SimpleDateFormat df = (SimpleDateFormat)ThreadDate.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_FORMAT);
            ThreadDate.set(df);
        }

        return df;
    }

    private static SimpleDateFormat TimeInstance() {
        SimpleDateFormat df = (SimpleDateFormat)ThreadTime.get();
        if (df == null) {
            df = new SimpleDateFormat(TIME_FORMAT);
            ThreadTime.set(df);
        }

        return df;
    }

    public static String currentDateTime() {
        return DateTimeInstance().format(new Date());
    }

    public static String dateTime(Date date) {
        return DateTimeInstance().format(date);
    }

    public static String dateTime(long date) {
        return DateTimeInstance().format(date);
    }

    public static String formatTime(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static Date dateTime(String datestr) throws ParseException {
        return DateTimeInstance().parse(datestr);
    }

    public static String currentDate() {
        return DateInstance().format(new Date());
    }

    public static String date(Date date) {
        return DateInstance().format(date);
    }

    public static Date date(String dateStr) throws ParseException {
        return DateInstance().parse(dateStr);
    }

    public static String currentTime() {
        return TimeInstance().format(new Date());
    }

    public static String time(Date date) {
        return TimeInstance().format(date);
    }

    public static Date time(String dateStr) throws ParseException {
        return TimeInstance().parse(dateStr);
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

    public static boolean isDate(String date) {
        try {
            DateTimeInstance().parse(date);
            return true;
        } catch (ParseException var2) {
            log.error("{}",var2);
            return false;
        }
    }

    public static long subtract(Date date1, Date date2) {
        long cha = (date2.getTime() - date1.getTime()) / 1000L;
        return cha;
    }

    public static long subtract(String date1, String date2) {
        long rs = 0L;

        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long cha = (end.getTime() - start.getTime()) / 1000L;
            rs = cha;
        } catch (ParseException var8) {
            log.error("{}",var8);
        }

        return rs;
    }

    public static int subtractMinute(String date1, String date2) {
        int rs = 0;

        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long cha = (end.getTime() - start.getTime()) / 1000L;
            rs = (int)cha / 60;
        } catch (ParseException var7) {
            log.error("{}",var7);
        }

        return rs;
    }

    public static int subtractMinute(Date date1, Date date2) {
        long cha = date2.getTime() - date1.getTime();
        return (int)cha / '\uea60';
    }

    public static int subtractHour(Date date1, Date date2) {
        long cha = (date2.getTime() - date1.getTime()) / 1000L;
        return (int)cha / 3600;
    }

    public static int subtractHour(String date1, String date2) {
        int rs = 0;

        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long cha = (end.getTime() - start.getTime()) / 1000L;
            rs = (int)cha / 3600;
        } catch (ParseException var7) {
            log.error("{}",var7);
        }

        return rs;
    }

    public static int subtractDay(String date1, String date2) {
        int rs = 0;

        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long sss = (end.getTime() - start.getTime()) / 1000L;
            rs = (int)sss / 86400;
        } catch (ParseException var7) {
            log.error("{}",var7);
        }

        return rs;
    }

    public static int subtractDay(Date date1, Date date2) {
        long cha = date2.getTime() - date1.getTime();
        return (int)cha / 86400000;
    }

    public static int subtractMonth(String date1, String date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        int result;
        try {
            c1.setTime(DateInstance().parse(date1));
            c2.setTime(DateInstance().parse(date2));
            int year1 = c1.get(1);
            int month1 = c1.get(2);
            int year2 = c2.get(1);
            int month2 = c2.get(2);
            if (year1 == year2) {
                result = month2 - month1;
            } else {
                result = 12 * (year2 - year1) + month2 - month1;
            }
        } catch (ParseException var9) {
            log.error("{}",var9);
            result = -1;
        }

        return result;
    }

    public static int subtractMonth(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(1);
        int month1 = c1.get(2);
        int year2 = c2.get(1);
        int month2 = c2.get(2);
        int result;
        if (year1 == year2) {
            result = month2 - month1;
        } else {
            result = 12 * (year2 - year1) + month2 - month1;
        }

        return result;
    }

    public static int subtractYear(String date1, String date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        int result;
        try {
            c1.setTime(DateInstance().parse(date1));
            c2.setTime(DateInstance().parse(date2));
            int year1 = c1.get(1);
            int year2 = c2.get(1);
            result = year2 - year1;
        } catch (ParseException var7) {
            log.error("{}",var7);
            result = -1;
        }

        return result;
    }

    public static int subtractYear(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(1);
        int year2 = c2.get(1);
        int result = year2 - year1;
        return result;
    }

    public static String subtractTime(String date1, String date2) {
        String result = "";

        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long sss = (end.getTime() - start.getTime()) / 1000L;
            int hh = (int)sss / 3600;
            int mm = (int)(sss - (long)(hh * 60 * 60)) / 60;
            int ss = (int)(sss - (long)(hh * 60 * 60) - (long)(mm * 60));
            result = hh + ":" + mm + ":" + ss;
        } catch (ParseException var10) {
            log.error("{}",var10);
        }

        return result;
    }

    public static String subtractDate(String date1, String date2) {
        String result = "";

        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long sss = (end.getTime() - start.getTime()) / 1000L;
            int dd = (int)sss / 86400;
            int hh = (int)(sss - (long)(dd * 60 * 60 * 24)) / 3600;
            int mm = (int)(sss - (long)(dd * 60 * 60 * 24) - (long)(hh * 60 * 60)) / 60;
            int ss = (int)(sss - (long)(dd * 60 * 60 * 24) - (long)(hh * 60 * 60) - (long)(mm * 60));
            result = dd + "-" + hh + ":" + mm + ":" + ss;
        } catch (ParseException var11) {
            log.error("{}",var11);
        }

        return result;
    }

    public static int subDay(Date startTime, Date endTime) {
        int days = 0;
        Calendar can1 = Calendar.getInstance();
        can1.setTime(startTime);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(endTime);
        int year1 = can1.get(1);
        int year2 = can2.get(1);
        Calendar can = null;
        if (can1.before(can2)) {
            days = days - can1.get(6);
            days += can2.get(6);
            can = can1;
        } else {
            days = days - can2.get(6);
            days += can1.get(6);
            can = can2;
        }

        for(int i = 0; i < Math.abs(year2 - year1); ++i) {
            days += can.getActualMaximum(6);
            can.add(1, 1);
        }

        return days;
    }

    public static int subDay(String startTime, String endTime) {
        int days = 0;

        try {
            Date date1 = DateInstance().parse(DateInstance().format(DateTimeInstance().parse(startTime)));
            Date date2 = DateInstance().parse(DateInstance().format(DateTimeInstance().parse(endTime)));
            Calendar can1 = Calendar.getInstance();
            can1.setTime(date1);
            Calendar can2 = Calendar.getInstance();
            can2.setTime(date2);
            int year1 = can1.get(1);
            int year2 = can2.get(1);
            Calendar can = null;
            if (can1.before(can2)) {
                days -= can1.get(6);
                days += can2.get(6);
                can = can1;
            } else {
                days -= can2.get(6);
                days += can1.get(6);
                can = can2;
            }

            for(int i = 0; i < Math.abs(year2 - year1); ++i) {
                days += can.getActualMaximum(6);
                can.add(1, 1);
            }
        } catch (ParseException var11) {
            log.error("{}",var11);
        }

        return days;
    }

    public static long subtimeBurst(String startDate, String endDate, String timeBurst) throws ParseException {
        Date start = DateTimeInstance().parse(startDate);
        Date end = DateTimeInstance().parse(endDate);
        return subtimeBurst(start, end, timeBurst);
    }

    public static long subtimeBurst(Date startDate, Date endDate, String timeBurst) throws ParseException {
        long second = 0L;
        Pattern p = Pattern.compile("^\\d{2}:\\d{2}-\\d{2}:\\d{2}");
        Matcher m = p.matcher(timeBurst);
        boolean falg = false;
        if (startDate.after(endDate)) {
            Date temp = startDate;
            startDate = endDate;
            endDate = temp;
            falg = true;
        }

        if (m.matches()) {
            String[] a = timeBurst.split("-");
            int day = subDay(startDate, endDate);
            if (day > 0) {
                long firstMintues = 0L;
                long lastMintues = 0L;
                long daySecond = 0L;
                String strDayStart = DateInstance().format(startDate) + " " + a[0] + ":00";
                String strDayEnd = DateInstance().format(startDate) + " " + a[1] + ":00";
                Date dayStart = DateTimeInstance().parse(strDayStart);
                Date dayEnd = DateTimeInstance().parse(strDayEnd);
                daySecond = subtract(dayStart, dayEnd);
                if ((startDate.after(dayStart) || startDate.equals(dayStart)) && startDate.before(dayEnd)) {
                    firstMintues = (dayEnd.getTime() - startDate.getTime()) / 1000L;
                } else if (startDate.before(dayStart)) {
                    firstMintues = (dayEnd.getTime() - dayStart.getTime()) / 1000L;
                }

                dayStart = DateTimeInstance().parse(DateInstance().format(endDate) + " " + a[0] + ":00");
                dayEnd = DateTimeInstance().parse(DateInstance().format(endDate) + " " + a[1] + ":00");
                if (endDate.after(dayStart) && (endDate.before(dayEnd) || endDate.equals(dayEnd))) {
                    lastMintues = (endDate.getTime() - dayStart.getTime()) / 1000L;
                } else if (endDate.after(dayEnd)) {
                    lastMintues = (dayEnd.getTime() - dayStart.getTime()) / 1000L;
                }

                second = firstMintues + lastMintues;
                second += (long)(day - 1) * daySecond;
            } else {
                String strDayStart = DateInstance().format(startDate) + " " + a[0] + ":00";
                String strDayEnd = DateInstance().format(startDate) + " " + a[1] + ":00";
                Date dayStart = DateTimeInstance().parse(strDayStart);
                Date dayEnd = DateTimeInstance().parse(strDayEnd);
                if ((startDate.after(dayStart) || startDate.equals(dayStart)) && startDate.before(dayEnd) && endDate.after(dayStart) && (endDate.before(dayEnd) || endDate.equals(dayEnd))) {
                    second = (endDate.getTime() - startDate.getTime()) / 1000L;
                } else {
                    if (startDate.before(dayStart)) {
                        if (endDate.before(dayEnd)) {
                            second = (endDate.getTime() - dayStart.getTime()) / 1000L;
                        } else {
                            second = (dayEnd.getTime() - dayStart.getTime()) / 1000L;
                        }
                    }

                    if (startDate.after(dayStart)) {
                        if (endDate.before(dayEnd)) {
                            second = (endDate.getTime() - startDate.getTime()) / 1000L;
                        } else {
                            second = (dayEnd.getTime() - startDate.getTime()) / 1000L;
                        }
                    }
                }

                if (startDate.before(dayStart) && endDate.before(dayStart) || startDate.after(dayEnd) && endDate.after(dayEnd)) {
                    second = 0L;
                }
            }
        } else {
            second = (endDate.getTime() - startDate.getTime()) / 1000L;
        }

        if (falg) {
            second = Long.parseLong("-" + second);
        }

        return second;
    }

    public static Date calculate(String date, int second, String timeBurst) {
        Date start = null;

        try {
            start = DateTimeInstance().parse(date);
            return calculate(start, second, timeBurst);
        } catch (ParseException var5) {
            log.error("{}",var5);
            return new Date();
        }
    }

    public static Date calculate(Date date, int second, String timeBurst) {
        Pattern p = Pattern.compile("^\\d{2}:\\d{2}-\\d{2}:\\d{2}");
        Matcher m = p.matcher(timeBurst);
        Calendar cal = Calendar.getInstance();
        if (m.matches()) {
            String[] a = timeBurst.split("-");

            try {
                Date dayStart = DateTimeInstance().parse(DateInstance().format(date) + " " + a[0] + ":00");
                Date dayEnd = DateTimeInstance().parse(DateInstance().format(date) + " " + a[1] + ":00");
                int DaySecond = (int)subtract(dayStart, dayEnd);
                int toDaySecond = (int)subtract(dayStart, dayEnd);
                int day;
                int remainder;
                if (second >= 0) {
                    if ((date.after(dayStart) || date.equals(dayStart)) && (date.before(dayEnd) || date.equals(dayEnd))) {
                        cal.setTime(date);
                        toDaySecond = (int)subtract(date, dayEnd);
                    }

                    if (date.before(dayStart)) {
                        cal.setTime(dayStart);
                        toDaySecond = (int)subtract(dayStart, dayEnd);
                    }

                    if (date.after(dayEnd)) {
                        cal.setTime(day(dayStart, 1));
                        toDaySecond = 0;
                    }

                    if (second > toDaySecond) {
                        day = (second - toDaySecond) / DaySecond;
                        remainder = (second - toDaySecond) % DaySecond;
                        cal.setTime(day(dayStart, 1));
                        cal.add(6, day);
                        cal.add(13, remainder);
                    } else {
                        cal.add(13, second);
                    }
                } else {
                    if ((date.after(dayStart) || date.equals(dayStart)) && (date.before(dayEnd) || date.equals(dayEnd))) {
                        cal.setTime(date);
                        toDaySecond = (int)subtract(date, dayStart);
                    }

                    if (date.before(dayStart)) {
                        cal.setTime(day(dayEnd, -1));
                        toDaySecond = 0;
                    }

                    if (date.after(dayEnd)) {
                        cal.setTime(dayEnd);
                        toDaySecond = (int)subtract(dayStart, dayEnd);
                    }

                    if (Math.abs(second) > Math.abs(toDaySecond)) {
                        day = (Math.abs(second) - Math.abs(toDaySecond)) / DaySecond;
                        remainder = (Math.abs(second) - Math.abs(toDaySecond)) % DaySecond;
                        cal.setTime(day(dayEnd, -1));
                        cal.add(6, Integer.valueOf("-" + day));
                        cal.add(13, Integer.valueOf("-" + remainder));
                    } else {
                        cal.add(13, second);
                    }
                }
            } catch (ParseException var13) {
                log.error("{}",var13);
            }
        } else {
            cal.setTime(date);
        }

        return cal.getTime();
    }

    public static boolean between(String startTime, String endTime, Date date) throws ParseException {
        return between(dateTime(startTime), dateTime(endTime), date);
    }

    public static boolean between(Date startTime, Date endTime, Date date) {
        return date.after(startTime) && date.before(endTime);
    }

    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static String getNow() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public static String getNow(String format) {
        return format(new Date(), format);
    }

    public static String getTimestampSecond() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }

    public static String format(Date date) {
        return date == null ? "" : format(date, getDatePattern());
    }

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }

        return returnValue;
    }

    public static String format(String pattern) {
        return format(new Date(), pattern);
    }


    public static List<String> getBetweeenTime(Date startDate, Date endDate, String pattern) {
        int type = 0;
        if (pattern.contains("dd")) {
            type = 5;
        } else if (pattern.contains("MM")) {
            type = 2;
        } else if (pattern.contains("yyyy")) {
            type = 1;
        }

        String strStartDate = format(startDate, pattern);
        String strEndDate = format(endDate, pattern);
        Date theEndDate = parse(strEndDate, pattern);
        Date currDate = parse(strStartDate, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        ArrayList ret = new ArrayList();

        while(!cal.getTime().after(theEndDate)) {
            ret.add(format(cal.getTime(), pattern));
            cal.add(type, 1);
        }

        return ret;
    }

    public static List<String> getDaysDates(int days) {
        Date startDate = getDateBeforeOrAfter(days);
        Date endDate = getDateBeforeOrAfter(-1);
        return getBetweeenTime(startDate, endDate, "yyyy-MM-dd");
    }

    public static Date getDateBeforeOrAfter(int iDate) {
        return getDateBeforeOrAfter((Date)null, iDate);
    }

    public static Date getDateBeforeOrAfter(Date date, int iDate) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTimeInMillis(date.getTime());
        }

        cal.add(5, iDate);
        return cal.getTime();
    }

    public static Date getNDate(Date currentDay, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(2, n);
        return calendar.getTime();
    }

    public static long getDiscrepantDays(Date beginDate, Date endDate) {
        return (endDate.getTime() - beginDate.getTime()) / 1000L / 60L / 60L / 24L;
    }

    public static Date getOffsetDate(Date d, int timeField, int time) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(timeField, now.get(timeField) + time);
        return now.getTime();
    }

    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    public static Date parseDate(String str) {
        Date ret = null;

        try {
            ret = (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
        } catch (Exception var3) {
            log.error("{}",var3);
        }

        return ret;
    }

    public static Date parseDate(String strDate, SimpleDateFormat fromat) {
        try {
            return fromat.parse(strDate);
        } catch (ParseException var3) {
            log.error("{}",var3);
            return null;
        }
    }

    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        try {
            return df.parse(strDate);
        } catch (ParseException var4) {
            return null;
        }
    }

    public static Date parse(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        try {
            return df.parse(df.format(date));
        } catch (ParseException var4) {
            return null;
        }
    }

    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, n);
        return cal.getTime();
    }

    public static Date subDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, cal.get(5) - n);
        return cal.getTime();
    }

    public static Date addYear(Date date, int n) {
        return addTime(date, n, 1);
    }

    public static Date addDay(Date date, int n) {
        return addTime(date, n, 5);
    }

    public static Date addHour(Date date, int n) {
        return addTime(date, n, 10);
    }

    public static String getTimeString() {
        return getTimeString(FORMAT_FULL);
    }

    public static String getTimeString(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static Date addTime(Date date, int n, int type) {
        if (date != null && n != 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(type, n);
            return cal.getTime();
        } else {
            return date;
        }
    }

    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    public static String getSeasonCn(Date date) {
        String season = null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        switch(month) {
            case 0:
            case 1:
            case 2:
                season = "一";
                break;
            case 3:
            case 4:
            case 5:
                season = "二";
                break;
            case 6:
            case 7:
            case 8:
                season = "三";
                break;
            case 9:
            case 10:
            case 11:
                season = "四";
        }

        return season;
    }

    public static int getSeason(Date date) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        switch(month) {
            case 0:
            case 1:
            case 2:
                season = 1;
                break;
            case 3:
            case 4:
            case 5:
                season = 2;
                break;
            case 6:
            case 7:
            case 8:
                season = 3;
                break;
            case 9:
            case 10:
            case 11:
                season = 4;
        }

        return season;
    }

    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int)(t / 1000L - t1 / 1000L) / 3600 / 24;
    }

    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int)(t / 1000L - t1 / 1000L) / 3600 / 24;
    }

    public static int countDays(Date date1, Date date2) {
        Date dateA;
        Date dateB;
        try {
            dateA = parse(date1, "yyyy-MM-dd");
            dateB = parse(date2, "yyyy-MM-dd");
        } catch (Exception var11) {
            return 0;
        }

        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        } else {
            if (dateA.getTime() < dateB.getTime()) {
                Date temp = dateA;
                dateA = dateB;
                dateB = temp;
            }

            Calendar cA = Calendar.getInstance();
            Calendar cB = Calendar.getInstance();
            cA.setTime(dateA);
            cB.setTime(dateB);
            long t1 = dateA.getTime();
            long t2 = dateB.getTime();
            return (int)(t1 / 1000L - t2 / 1000L) / 3600 / 24;
        }
    }

    public static int compareDate(Date date1, Date date2, int stype) {
        int n = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        while(!c1.after(c2)) {
            ++n;
            if (stype == 1) {
                c1.add(2, 1);
            } else {
                c1.add(5, 1);
            }
        }

        --n;
        if (stype == 2) {
            n /= 365;
        }

        return n;
    }

    public static int compareDate(String date1, String date2, int stype) {
        int n = 0;
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception var9) {
            ;
        }

        while(!c1.after(c2)) {
            ++n;
            if (stype == 1) {
                c1.add(2, 1);
            } else {
                c1.add(5, 1);
            }
        }

        --n;
        if (stype == 2) {
            n /= 365;
        }

        return n;
    }

    public static int compareDate(Date date1, Date date2) {
        if (date1 != null && null != date2) {
            try {
                if (date1.getTime() > date2.getTime()) {
                    return 1;
                } else {
                    return date1.getTime() < date2.getTime() ? -1 : 0;
                }
            } catch (Exception var3) {
                log.error("{}",var3);
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    public static int getMonths(String date1, String date2) {
        Date dateA = parse(date1, "yyyy-MM-dd");
        Date dateB = parse(date2, "yyyy-MM-dd");
        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        } else {
            if (dateA.getTime() < dateB.getTime()) {
                Date temp = dateA;
                dateA = dateB;
                dateB = temp;
            }

            Calendar cA = Calendar.getInstance();
            Calendar cB = Calendar.getInstance();
            cA.setTime(dateA);
            cB.setTime(dateB);
            int year = cA.get(1) - cB.get(1);
            int month = cA.get(2) - cB.get(2);
            if (year > 0) {
                if (month > 0) {
                    if (cB.get(5) > cA.get(5)) {
                        --month;
                    } else if (cB.get(5) == cA.get(5)) {
                        ;
                    }
                } else if (month == 0) {
                    if (cB.get(5) > cA.get(5)) {
                        month = -1;
                    } else if (cB.get(5) == cA.get(5)) {
                        ;
                    }
                } else if (cA.get(5) <= cB.get(5) && cA.get(5) != cB.get(5)) {
                    --month;
                }
            } else if (month > 0) {
                if (cB.get(5) > cA.get(5)) {
                    --month;
                } else if (cB.get(5) == cA.get(5)) {
                    ;
                }
            }

            return year * 12 + month;
        }
    }

    public static Date startOfDay(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
            return cal.getTime();
        }
    }

    public static Date endOfDay(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(11, 23);
            cal.set(12, 59);
            cal.set(13, 59);
            cal.set(14, 999);
            return cal.getTime();
        }
    }

    public static Date getFirstDayOfWeek(int year, int week) {
        --week;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, year);
        calendar.set(2, 0);
        calendar.set(5, 1);
        Calendar cal = (Calendar)calendar.clone();
        cal.add(5, week * 7);
        return getFirstDayOfWeek(cal.getTime());
    }

    public static Date getLastDayOfWeek(int year, int week) {
        --week;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, year);
        calendar.set(2, 0);
        calendar.set(5, 1);
        Calendar cal = (Calendar)calendar.clone();
        cal.add(5, week * 7);
        return getLastDayOfWeek(cal.getTime());
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getFirstDayOfWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    public static Date getLastDayOfWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    public static Date getLastDayOfLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfWeek(calendar.get(1), calendar.get(3) - 1);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(1);
        }

        if (month == null) {
            month = calendar.get(2);
        }

        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        calendar.roll(5, -1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), 0, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), 11, 31);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(1);
        }

        if (month == null) {
            month = calendar.get(2);
        }

        calendar.set(year, month, 1);
        calendar.roll(5, -1);
        return calendar.getTime();
    }

    public static Date getLastDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2) - 1, 1);
        calendar.roll(5, -1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFirstDayOfQuarter(calendar.get(1), getQuarterOfYear(date));
    }

    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        new Integer(0);
        Integer month;
        if (quarter == 1) {
            month = 0;
        } else if (quarter == 2) {
            month = 3;
        } else if (quarter == 3) {
            month = 6;
        } else if (quarter == 4) {
            month = 9;
        } else {
            month = calendar.get(2);
        }

        return getFirstDayOfMonth(year, month);
    }

    public static Date getLastDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfQuarter(calendar.get(1), getQuarterOfYear(date));
    }

    public static Date getFirstDayOfNextQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTime(getLastDayOfQuarter(calendar.get(1), getQuarterOfYear(date)));
        calendar.add(6, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfNextQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, 3);
        calendar.setTime(getLastDayOfQuarter(calendar.get(1), getQuarterOfYear(calendar.getTime())));
        calendar.add(6, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        new Integer(0);
        Integer month;
        if (quarter == 1) {
            month = 2;
        } else if (quarter == 2) {
            month = 5;
        } else if (quarter == 3) {
            month = 8;
        } else if (quarter == 4) {
            month = 11;
        } else {
            month = calendar.get(2);
        }

        return getLastDayOfMonth(year, month);
    }

    public static Date getLastDayOfLastQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfLastQuarter(calendar.get(1), getQuarterOfYear(date));
    }

    public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        new Integer(0);
        Integer month;
        if (quarter == 1) {
            month = 11;
        } else if (quarter == 2) {
            month = 2;
        } else if (quarter == 3) {
            month = 5;
        } else if (quarter == 4) {
            month = 8;
        } else {
            month = calendar.get(2);
        }

        return getLastDayOfMonth(year, month);
    }

    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) / 3 + 1;
    }

    public static int getMonthsDiff(String startDate, String endDate) {
        Date dateA = parse(startDate, "yyyy-MM-dd");
        Date dateB = parse(endDate, "yyyy-MM-dd");
        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        } else {
            Calendar cA = Calendar.getInstance();
            Calendar cB = Calendar.getInstance();
            cA.setTime(dateA);
            cB.setTime(dateB);
            int year = cB.get(1) - cA.get(1);
            int month = cB.get(2) - cA.get(2);
            return year * 12 + month;
        }
    }

    public static List<String> getMonthsList(String startDate, String endDate) {
        List<String> monthsList = new ArrayList();
        Calendar cal = Calendar.getInstance();
        Date date = parse(startDate, FORMAT_SHORT);
        cal.setTime(date);
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMM");
        int months = getMonthsDiff(startDate, endDate);

        for(int i = 0; i <= months; ++i) {
            cal.setTime(date);
            cal.add(2, i);
            monthsList.add(simple.format(cal.getTime()));
        }

        return monthsList;
    }

    public static String getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearFirst(currentYear);
    }

    public static String getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearLast(currentYear);
    }

    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        return format(currYearFirst, FORMATDAY);
    }

    public static String getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        Date currYearLast = calendar.getTime();
        return format(currYearLast, FORMATDAY);
    }

    public static int getAgeByBirth(Date birthday) {
        boolean var1 = false;

        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);
            int age;
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(1) - birth.get(1);
                if (now.get(6) > birth.get(6)) {
                    ++age;
                }
            }

            return age;
        } catch (Exception var4) {
            return 0;
        }
    }

    public static List<String> process(String date1, String date2) throws ParseException {
        List<String> timeList = new ArrayList();
        String dateFormat = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        if (date1.equals(date2)) {
            timeList.add(date1);
            return timeList;
        } else {
            String tmp = "";
            if (date1.compareTo(date2) > 0) {
                tmp = date1;
                date1 = date2;
                date2 = tmp;
            }

            tmp = format.format(strToDate(date1).getTime() + 86400000L);
            timeList.add(date1);

            while(tmp.compareTo(date2) <= 0) {
                timeList.add(tmp);
                tmp = format.format(strToDate(tmp).getTime() + 86400000L);
            }

            return timeList;
        }
    }

    public static Date strToDate(String str) {
        return strToDate(str, FORMATDAY);
    }

    public static Date strToDate(String str, String fmt) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fmt);
            Date date = format.parse(str);
            return date;
        } catch (Exception var4) {
            return null;
        }
    }

    public static Date addMinute(Date date, int n) {
        return addTime(date, n, 12);
    }

    public static Date addSecond(Date date, int n) {
        return addTime(date, n, 13);
    }

    public static String getYesterdayDate() {
        Calendar c = Calendar.getInstance();
        c.add(5, -1);
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    public static String addDay(String dateString, int n) {
        Calendar cal = Calendar.getInstance();
        Date date = parse(dateString, FORMATDAY);
        cal.setTime(date);
        cal.add(5, n);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(cal.getTime());
    }

    public static String convertToFormatShort(String dateStr) {
        Calendar cal = Calendar.getInstance();
        Date date = parse(dateStr, FORMATDAY);
        cal.setTime(date);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(cal.getTime());
    }

    static {
        DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        DATE_FORMAT = "yyyy-MM-dd";
        TIME_FORMAT = "HH:mm:ss";
        ThreadDateTime = new ThreadLocal();
        ThreadDate = new ThreadLocal();
        ThreadTime = new ThreadLocal();
    }

}

