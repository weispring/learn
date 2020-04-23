package com.lxc.learn.junit.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/23
 */
@Slf4j
public class RedisConstant {

    public static final String USER_CREATE_ORDER_LIMIT_KEY = "USER_CREATE_ORDER_LIMIT_KEY";
    //下单笔数限制
    public static final Long USER_CREATE_ORDER_LIMIT_NUM = 2L;

    private static final String split = "-";

    public static String getUserCreateOrderLimitKey(String buyerPhone){
        return USER_CREATE_ORDER_LIMIT_KEY + split + buyerPhone;
    }

}
