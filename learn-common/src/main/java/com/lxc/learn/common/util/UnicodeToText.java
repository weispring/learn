package com.lxc.learn.common.util;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/7/9
 **/
public class UnicodeToText {

    public static void main(String[] args) {
        String s = "\\u8be5\\u7528\\u6237\\u4e0d\\u5b58\\u5728\\uff0c\\u8bf7\\u7ba1\\u7406\\u5458\\u6dfb\\u52a0\\u540e\\u518d\\u4f7f\\u7528";
        String b = unicodeToCn(s);
        System.out.println(b);
    }

    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        if ("".equals(returnStr)){
            return unicode;
        }
        return returnStr;
    }

}
