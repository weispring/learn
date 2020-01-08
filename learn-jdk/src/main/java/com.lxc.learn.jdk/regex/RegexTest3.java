package com.lxc.learn.jdk.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
}
