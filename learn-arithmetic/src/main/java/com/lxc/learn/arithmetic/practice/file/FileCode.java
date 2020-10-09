package com.lxc.learn.arithmetic.practice.file;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Random;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/5
 */
@Slf4j
public class FileCode {

    /** 为3和4的倍数，避免解码的时候，读取的数据无法解码 */
    private static final Integer BYTE_SIZE = 3 * 1024;

    private static String source = "E:\\own-test\\source.txt";

    private static String encode = "E:\\own-test\\encode.txt";

    private static String decode = "E:\\own-test\\decode.txt";


    public static void encode(String fileSource,String targetFile){
        try {
            FileInputStream fis = new FileInputStream(fileSource);
            FileOutputStream fos = new FileOutputStream(targetFile);

            byte[] data = new byte[BYTE_SIZE];
            int length;
            while ((length = fis.read(data)) != -1){

                fos.write(java.util.Base64.getEncoder().encode(removeBlank(data,length)));
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    public static void decode(String fileSource,String targetFile){
        try {
            FileInputStream fis = new FileInputStream(fileSource);
            FileOutputStream fos = new FileOutputStream(targetFile);

            new Random().nextInt();
            byte[] data = new byte[randonInt()];
            int length;
            while ((length = fis.read(data)) != -1){
                fos.write(java.util.Base64.getDecoder().decode(removeBlank(data,length)));
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }


    /**
     * 移除不存在的byte，避免产生多余数据
     * @param data
     * @param length
     * @return
     */
    private static byte[] removeBlank(byte[] data, int length){
        byte[] bytes = new byte[length];
        System.arraycopy(data,0,bytes,0,length);
        System.out.println(bytes.length);
        return bytes;
    }

    public static void main(String[] args) {

        System.out.println("=".getBytes());
        encode(source,encode);
        decode(encode,decode);
    }


    public static int randonInt(){
        int[] randoms = new int[]{3,5,7,9,11,15,18,21,33,99};
        return randoms[new Random().nextInt(10)];
    }
}
