package com.lxc.learn.arithmetic.bigfile.bigfilesort;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/4
 */
@Slf4j
public class GenerateFileTask {


    /**
     * 创建文件
     *
     * @param filePath
     * @throws Exception
     */
    public static void generateTestFile(String filePath) throws Exception {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        FileChannel channel = null;
        RandomAccessFile rf = null;
        try {
            File testFile = new File(filePath);
            if (testFile.exists())
                testFile.delete();
            // 如果文件不存在则创建
            testFile.createNewFile();
            rf = new RandomAccessFile(testFile, "rw");
            channel = rf.getChannel();
            int intSize = 4 ;
            int capacity = intSize * 10000 * 2 * 10 / 4 + 1; // 约40kb
            ByteBuffer writerBuffer = ByteBuffer.allocate(capacity);
            // 一千万
            for (long i = 0; i <= 10000 * 1000; i++) {
                sb.append(random.nextInt(100000)).append("\n");
                // 刷新缓冲
                if ((i + 1) % 10000 == 0) {
                    writerBuffer.put(sb.toString().getBytes());
                    sb.setLength(0);
                    writerBuffer.flip();
                    channel.write(writerBuffer);
                    writerBuffer.clear();
                }
            }
            if (sb.length() > 0) {
                writerBuffer.put(sb.toString().getBytes());
                writerBuffer.flip();
                channel.write(writerBuffer);
                writerBuffer.clear();
            }
        } catch (IOException e) {
            System.out.println("生成测试文件失败！" + e.getMessage());
            throw e;
        } finally {
            try {
                if (rf != null) {
                    rf.close();
                }
            } catch (IOException e) {
                // no-op
            }
            try {
                if (channel.isOpen()) {
                    channel.close();
                }
            } catch (IOException e) {
                // no-op
            }
        }
    }
}
