package com.lxc.learn.doc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/5 19:27
 * @Description:
 */
@Slf4j
public class DemoB extends DemoA {

    public int m1 (int a, int b) { return 0; }
    private int m1 (int a, long b) { return 0; }

    public static void main(String[] args) {
        String regex = "\\d+\\.?\\d*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("1.25");
        if (matcher.matches()){
            log.info("已经匹配:{}","");
        }

    }
}
