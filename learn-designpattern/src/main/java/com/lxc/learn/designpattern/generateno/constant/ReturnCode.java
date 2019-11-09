package com.lxc.learn.designpattern.generateno.constant;

import com.lxc.learn.common.util.core.IReturnCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 14:51
 */
@Slf4j
@AllArgsConstructor
public enum  ReturnCode implements IReturnCode {

    NOT_SUPPORTED_TRANSACTION_TYPE("designpattern_1001","不支持的生成唯一流水号的项目类型"),
    LOCK_FAIL("designpattern_1002","获取锁失败"),
    NOT_SUPPORTED_BUSINESS_TYPE("designpattern_1003","不支持的业务类型"),
    INCR_FAIL("designpattern_1004","transactionId 自增失败"),
    INCR_LIMIT("designpattern_1005","transactionId超过最大值"),
    LOCK_INTERRUPTED("designpattern_1006","lock interrupted"),

    ;


    private String code;

    private String msg;


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getError() {
        return null;
    }
}
