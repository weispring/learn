package com.lxc.learn.jdk.format;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/27
 */
@Slf4j
public class MessageFormatTest {

    public static void main(String[] args) {
        Object[] testArgs = {new String("张三"),new String("大傻子")};
        MessageFormat form = new MessageFormat("{0}是个{1}");
        String format = form.format(testArgs);
        System.out.println(format);
    }
}
