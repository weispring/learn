package com.lxc.learn.jdk.modifier.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/20
 */
@Slf4j
public class ModifierTest {

    public static void main(String[] args) {
        Default1 default1 = new Default1();
        default1.defaultMethod();
    }
}
