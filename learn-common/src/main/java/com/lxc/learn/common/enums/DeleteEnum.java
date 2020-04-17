package com.lxc.learn.common.enums;

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
public enum  DeleteEnum {

    NORMAL(1),
    DELETED(2),
    ;

    private Integer code;


}
