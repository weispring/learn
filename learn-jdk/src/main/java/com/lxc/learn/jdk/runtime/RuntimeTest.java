package com.lxc.learn.jdk.runtime;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/29 16:10
 */
@Slf4j
public class RuntimeTest {


    public static void main(String[] args) throws Exception {
        byte[] bytes = new byte[8192];
        Runtime.getRuntime().exec("cd f: && cd learn && git log -2").getOutputStream().write(bytes);

        System.out.println(new String(bytes));
    }

}
