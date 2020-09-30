package com.lxc.learn.jdk.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Auther: lixianchun
 * @Date: 2020/9/9 22:29
 * @Description:
 */
public class StreamClose {

    public static void main(String[] args) throws IOException {
        testFileStream();
    }


    private static void testFileStream() throws IOException {
        String path = "E:\\lcode\\code\\learn\\learn-jdk\\src\\main\\java\\com.lxc.learn.jdk\\io\\UrlTest.java";
        FileInputStream fis = new FileInputStream(new File(path));

        fis.close();

        BufferedOutputStream bufferedOutputStream = null;
        bufferedOutputStream.close();
    }

}
