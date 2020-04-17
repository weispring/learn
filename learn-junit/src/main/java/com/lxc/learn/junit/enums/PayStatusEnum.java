package com.lxc.learn.junit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/17
 */
@Slf4j
@AllArgsConstructor
@Getter
public enum PayStatusEnum {
    //支付状态（10：待支付；20：支付成功；30：支付失败；40：待退款；50：退款成功；60：退款失败）
    WAIT_PAY(10),
    PAID(20),
    PAY_FAIL(30),
    WAIT_REFUND(40),
    REFUNDED(50),
    REFUND_FAIL(60),
    ;

    private Integer code;
}
