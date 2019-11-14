package com.lxc.learn.common.util.web;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/18 16:20
 */
@Slf4j
public class StringUtil {

    public static boolean isEmpty(Object str){
        return (str == null || str.toString().length() == 0)?true:false;
    }

    public static boolean isNotEmpty(Object str){
        return !isEmpty(str);
    }

    public static String subExceed(String str, int length) {
        return !isEmpty(str) && length < str.length() ? str.substring(0, length) : str;
    }

    public static boolean isBlank(Object str){
        return (str == null || str.toString().length() == 0)?true:false;
    }

    public static boolean isNotBlank(Object str){
        return !isBlank(str);
    }


}
