package com.lxc.learn.common.test.classpath;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: lixianchun
 * @Date: 2019/8/11 07:44
 * @Description:
 */
@Slf4j
public class FileLoad {
    public static void main(String[] args) {
        FileLoad fileLoad = new FileLoad();
        String path = fileLoad.getClass().getResource("").getPath();
        //得到的是当前类class文件的URI目录。不包括自己！
        log.info("{}",path);


        //得到的是当前的classpath的绝对URI路径
        path = fileLoad.getClass().getResource("/").getPath();
        log.info("{}",path);

        path = fileLoad.getClass() .getClassLoader().getResource("").getPath();
        log.info("{}",path);

        path = ClassLoader.getSystemResource("").getPath();
        log.info("{}",path);
        path = Thread.currentThread().getContextClassLoader ().getResource("").getPath();
        log.info("{}",path);
    }
}
