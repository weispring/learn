package com.lxc.learn.jdk.file.image;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.*;
import java.security.Permission;
import java.util.EnumSet;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/23
 */


/**
 * 最近的项目中使用Itext将txt文件转换为PDF文件， 并且实现对文件的一些权限控制。 现实对pdf文件加
 *密，添加水印等。
 */
public class PdfEncryption
{
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        String user = "123456";//read
        String own = "123123";//write
        String path = "C:\\Users\\vpclub\\Desktop\\Java虚拟机（第二版） - 副本 (2).pdf";
        try{
            PdfReader reader = new PdfReader(path);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("./12030.pdf"));
            // 设置密码
            stamper.setEncryption(user.getBytes(), own.getBytes(), 16, false);

            stamper.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}