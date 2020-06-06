package com.lxc.learn.jdk.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@Slf4j
public class RegexTest3 {
    @Test
    public void test(){
        /**
         * freight-admin-consumer-5c974c565b-4mv8t
         freight-consumer-5c8745b596-78nk2
         freight-provider-677c9d6d58-wmlj8
         */
        String s = "freight-admin-consumer-5c974c565b-4mv8t";
        Pattern pattern = Pattern.compile("([a-z0-9]+)-([a-z0-9]+)-([a-z0-9]+)-([a-z0-9]+)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()){
            int i = matcher.groupCount();
            //<=
            for (int j=0;j<=i;j++){
                String value = matcher.group(j);
                System.out.println(value);
            }
        }
    }

    @Test
    public void test11(){
        RegexBase.test("^[A-Z]{5}_\\d{12}.[A-Za-z]$", Arrays.asList("66ADDDC_111111111111.RdD44"));
        RegexBase.test("[A-Z]{5}_\\d{12}.[A-Za-z]{3}$", Arrays.asList("66ADDDC_111111111111.RdD"));
        RegexBase.test("[A-Z]{5}_\\d{12}.[A-Za-z]$", Arrays.asList("66ADDDC_111111111111.t"));

        RegexBase.test("\\d{2}", Arrays.asList("1"));
        RegexBase.test("[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])", Arrays.asList("20200101230101"));
        RegexBase.test("[0-1][0-9]|2[0-4]", Arrays.asList("12","00","24"));
        RegexBase.test("\\d+\\.\\d{2}", Arrays.asList("12","00","24","12.33","12▪33"));
        RegexBase.test("^[1-9]\\d*", Arrays.asList("1eee","11","r","-33","12▪33"));
    }
}
