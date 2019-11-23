package com.lxc.learn.jdk.reflect;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author lixianchun
 * @Description
 * @date 2019/11/22 15:19
 */

@Slf4j
public class MyBean extends ParentMyBean implements IMyBean {

    private String BEAN_NAME = "BEAN_NAME";

    private final static Object BEAN_NAME_STATIC = "BEAN_NAME_STATIC";

    @Override
    public void test() {

    }

    private void reflect(){
        System.out.println("invoke private method by reflect ");
    }
}
