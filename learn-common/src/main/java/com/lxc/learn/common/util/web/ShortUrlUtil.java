package com.lxc.learn.common.util.web;

import com.lxc.learn.common.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/18 16:13
 */
@Slf4j
public class ShortUrlUtil {

    static String[] chars = new String[]{          //要使用生成URL的字符
            "a","b","c","d","e","f","g","h",
            "i","j","k","l","m","n","o","p",
            "q","r","s","t","u","v","w","x",
            "y","z","0","1","2","3","4","5",
            "6","7","8","9","A","B","C","D",
            "E","F","G","H","I","J","K","L",
            "M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z"
    };


    /**
     * 1.自增序列算法,避免顺序，打乱chars顺序
     * 2.摘要算法
     * @param args
     */
    public static void main(String[] args) {
       /* String url = "http://www.sunchis.com";
        for (String string : ShortText(url)) {
            log.info(string);
        }*/
       for (int i=0;i<10;i++){
           try {
               Thread.sleep(1);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           Long a = System.currentTimeMillis();
           log.info("测试自增序列算法:{}",to62(a));
       }

    }



    public static String[] ShortText(String string){
        String key = "shortUrl";                 //自己定义生成MD5加密字符串前的混合KEY
        String hex = Md5.md5(key + string, SystemConstant.MD5);
        int hexLen = hex.length();
        int subHexLen = hexLen / 8;
        String[] ShortStr = new String[4];

        for (int i = 0; i < subHexLen; i++) {
            String outChars = "";
            int j = i + 1;
            String subHex = hex.substring(i * 8, j * 8);
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

            for (int k = 0; k < 6; k++) {
                int index = (int) (Long.valueOf("0000003D", 16) & idx);
                outChars += chars[index];
                idx = idx >> 5;
            }
            ShortStr[i] = outChars;
        }
        return ShortStr;
    }


    public static String to62(Long i) {
        long q = 1,r;
        long o = i;
        StringBuilder sb = new StringBuilder();
        while (q != 0) {
            q = i / 62;
            // really: r = i - (q * 100);
            r = i - q * 62;
            i = q;
            sb.append(chars[(int) r]);
        }

        char[] arr = sb.toString().toCharArray();
        for (int m=0, mid=arr.length>>1, j=arr.length-1; m<mid; m++, j--){
            char f = arr[m];
            arr[m] = arr[j];
            arr[j] = f;
        }
        return new String(arr);
    }

}
