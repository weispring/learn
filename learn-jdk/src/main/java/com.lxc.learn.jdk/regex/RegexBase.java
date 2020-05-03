package com.lxc.learn.jdk.regex;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/3
 */
@Slf4j
public class RegexBase {
    public static void test(String regex, List str){
        Pattern pattern = Pattern.compile(regex);
        for (Object s : str){
            Matcher matcher = pattern.matcher(s.toString());
            if (matcher.find()){
                log.info("regex {} 匹配 : {} ",regex,matcher.group());
            }else {
                log.info("regex {} 不匹配 : {} ",regex,s);
            }

           /*while (matcher.find()){
               log.info("regex {} 匹配 : {} ",regex,matcher.group());
           }*/
        }

    }

    public static void testMulti(String regex, List str){
        Pattern pattern = Pattern.compile(regex);
        for (Object s : str){
            Matcher matcher = pattern.matcher(s.toString());
           while (matcher.find()){
               log.info("regex {} 匹配 : {} ",regex,matcher.group());
           }
        }
    }

}
