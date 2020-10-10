package com.lxc.learn.jdk.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/24
 */
@Slf4j
public class Base64Image {

    public static void main(String[] args) throws Exception{
    /*    String path = "C:\\Users\\vpclub\\Desktop\\微信截图_20200324194511.png";
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(path);
            byte[] bytes = new byte[1024];

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len=0;
            while ((len = fis.read(bytes)) != -1){
                out.write(bytes,0,len);
            }
            String base64 = Base64Utils.encodeToString(out.toByteArray());
            log.error(base64);
            String newFile = "./new.jpg";
            generateImage(base64,newFile);

        }catch (Exception e){

        }finally {
            if (fis != null){
                fis.close();
            }
            if (fos != null){
                fos.close();
            }
        }*/
    System.out.println(Integer.toBinaryString("a".getBytes()[0]));
    String a = "spring你就好好";
    System.out.println("\n原始 ");
    StringBuffer sb = new StringBuffer();
    for (byte b : a.getBytes()){
        String binaryString = Integer.toBinaryString(b);
        while (binaryString.length() < 8){
            binaryString = "0" + binaryString;
        }
        System.out.print(binaryString);
        sb.append(binaryString);
    }
    System.out.println("\nencode ");




    StringBuffer sbencode = new StringBuffer();
    byte[] encode = Base64Utils.encode(a.getBytes());
    for (byte b : encode){
        String binaryString = Integer.toBinaryString(b);
        while (binaryString.length() < 8){
            binaryString = "0" + binaryString;
        }
        System.out.print(binaryString);
        sbencode.append(binaryString);
    }

    //log.info("encode: {}",sbencode.toString());

    System.out.println("\n还原:");
    // 这里的byte 数组长度应当是 原始的
    byte[] decode = Base64Utils.decode(encode);
    for (byte b : decode){
        String binaryString = Integer.toBinaryString(b);
        while (binaryString.length() < 8){
            binaryString = "0" + binaryString;
        }
        System.out.print(binaryString);
    }

    System.out.println("\n");
    System.out.println(new String(decode));


    }


    public static boolean generateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out =  null;
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// TODO EEROR  调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }


}
