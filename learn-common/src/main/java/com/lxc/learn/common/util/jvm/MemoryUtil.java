package com.lxc.learn.common.util.jvm;

import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/12 9:32
 */
@Slf4j
public class MemoryUtil {

    public static void main(String[] args) throws IOException {

        //TYPE_BYTE_BINARY
        //imageType 决定占用的空间大小
        BufferedImage combined = new BufferedImage(1000, 1000, BufferedImage.TYPE_BYTE_GRAY);
      /*  currentMemory();
        int[] a = new int[1024000];//4M
        currentMemory();
        int[] b = new int[102400000];
        currentMemory();*/

    }
    public static void currentMemory(){
        Long totalByte = Runtime.getRuntime().totalMemory();
        Long freeByte = Runtime.getRuntime().freeMemory();

        Long total = totalByte / 1024 / 1024;
        Long free = freeByte / 1024 / 1024;
        log.info("当前占用内存：{}, total:{},free{}",(total - free), total, free);
    }

    /** todo error agent ObjectSizeFetcher
     * 如何计算Java 某个对象占用多大的内存
     */



    /**
     * 解决内存溢出
     * BufferedImage combined = new BufferedImage(1000, 1000, BufferedImage.TYPE_BYTE_GRAY);
     */
}
