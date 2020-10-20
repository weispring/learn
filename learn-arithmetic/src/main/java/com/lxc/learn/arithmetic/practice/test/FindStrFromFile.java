package com.lxc.learn.arithmetic.practice.test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
@Slf4j
public class FindStrFromFile {

    private static String STR_COMMON = "abc";

    private static long total;
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\vpclub\\Desktop\\test.txt";

        BufferedReader br = null;
        String currentLine = null;
        String prevLine = null;

        String prevChar = null;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((currentLine = br.readLine()) != null){
                exist(currentLine);
                if (prevLine != null){
                    String str = "";
                    String pline = null;
                    if (prevLine.length() < STR_COMMON.length()){
                        str = prevLine;
                    }else {
                        str = prevLine.substring(prevLine.length() - STR_COMMON.length() + 1);
                        pline = prevLine;
                    }
                    //需要考虑 当前字符的长度 小于 需要的长度
                    str = str + currentLine.substring(0,
                            (currentLine.length() >= STR_COMMON.length() - 1) ?  (STR_COMMON.length() - 1) : currentLine.length());
                    if (str.length() >= STR_COMMON.length()){
                        int result = exist(str);
                        //处理查找拼接后的字符串，若找到时，且index 为0时，前一个字符在上一行
                        if (result == 0
                                && pline != null){
                            list.remove(list.size() - 1);
                            list.add(pline.substring(pline.length() - STR_COMMON.length(),pline.length() - STR_COMMON.length() + 1));
                        }
                    }
                }
                prevLine = currentLine;
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            log.info("total: {}", total);
            for (String s : list){
                log.info(s);
            }
            if (br != null){
                br.close();
            }
        }
    }

    private static int exist(String str){
        int index = str.indexOf(STR_COMMON);
        if (index != -1){
            total ++ ;
            if (index == 0){
                list.add("");
            }else {
                list.add(str.substring(index-1, index));
            }
        }
        return index;
    }

}
