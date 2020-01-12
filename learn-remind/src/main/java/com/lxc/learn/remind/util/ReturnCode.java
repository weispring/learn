package com.lxc.learn.remind.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: lixianchun
 * Date: 2019/3/31
 * Description:
 */
@AllArgsConstructor
@Getter
public enum  ReturnCode implements IReturnCode {


    SUCCESS("1000","success"),

    FAIL("5000","fail");

    private String code;


    private String msg;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
