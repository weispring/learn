package com.lxc.learn.common.test;

import lombok.extern.slf4j.Slf4j;

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
        byte[] en = java.util.Base64.getEncoder().encode(a.getBytes());
        log.info("en：{}",en.length);
        log.info("en string :{}",new String(en).getBytes().length);
        byte[] de = java.util.Base64.getDecoder().decode(en);
        log.info("de：{}",de.length);
        String b = new String(de);
        log.info("{}",b);
        log.info("b:{}",b.getBytes().length);
}
}
