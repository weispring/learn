package com.lxc.learn.common.test;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lixianchun
 * @Date: 2019/11/28 22:26
 * @Description:
 */
@Slf4j
public class Base64 {

    public static void main(String[] args) {
        String a = "于千万人之中遇见你所要遇见的人，于千万年之中，时间的无涯的荒野里，没有早一步，也没有晚一步，刚巧赶上了，那也没有别的话可说，惟有轻轻地问一声：“噢，你也在这里吗？";
        int l = a.getBytes().length;
        log.info("原始字节：{}",l);

        byte[] data = new byte[a.getBytes().length + 5];
        System.arraycopy(a.getBytes(),0,data,0,a.getBytes().length);

        byte[] en = java.util.Base64.getEncoder().encode(a.getBytes());
        log.info("en：{}",en.length);

        byte[] en1 = java.util.Base64.getEncoder().encode(data);
        log.info("en1：{}",en1.length);

        log.info("en string :{}",new String(en));
        log.info("en string :{}",new String(en1));
        byte[] de = java.util.Base64.getDecoder().decode(en);
        log.info("de：{}",new java.lang.String(de));

        byte[] de1 = java.util.Base64.getDecoder().decode(en1);
        log.info("de：{}", new java.lang.String(de1));
    }

}
