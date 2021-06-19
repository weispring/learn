package com.lxc.learn.jdk.clasload;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/24
 */
@Slf4j
public class TestA {

    public static String getRandomStr(){
        log.info("{}",TestA.class.getName());
        return String.valueOf(new Random().nextInt(1000));
    }
}
