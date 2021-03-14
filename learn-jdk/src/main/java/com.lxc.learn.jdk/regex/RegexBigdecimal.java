package com.lxc.learn.jdk.regex;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/3/6
 **/
public class RegexBigdecimal {
    public static void main(String[] args) {
        //校验小数范围，整数部门最长13位，小数部分最长为4位
        Pattern pattern = Pattern.compile("^0|[1-9]\\d{0,12}|[1-9]\\d{0,12}\\.\\d{1,4}|0\\.\\d{1,4}$");
        for (String s : Arrays.asList("00.34","00.4", "30.4444", "0.0", "1", "11111111111111")){
            if (pattern.matcher(s).matches()){
                System.out.println(s);
            }
        }
    }

    @Test
    public void test(){
        List list = Arrays.asList("001", "002", "00005", "1", "9", "10");
        Collections.sort(list);
        System.out.println(list);
    }
}
