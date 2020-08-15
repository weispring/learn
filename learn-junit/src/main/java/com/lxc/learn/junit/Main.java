package com.lxc.learn.junit;

import com.lxc.learn.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/1
 */
@Slf4j
public class Main {

    /**
     *
     */

    public static void main(String[] args) throws IOException {
        String a = "https://umall.hk.chinamobile.com/M00/11/69//CgADU18zbEWAQ6jlABlhejIhe2c83.jpeg";
        a = a.substring(a.lastIndexOf("."));

        if (a.matches("(.jpg|.jpeg|.gif|.png|.bmp)$")){
            log.error("=====================");
        }
        System.out.println(a);
    }

}
