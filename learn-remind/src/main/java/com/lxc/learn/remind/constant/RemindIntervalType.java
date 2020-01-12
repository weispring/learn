package com.lxc.learn.remind.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: lixianchun
 * @Date: 2020/1/12 19:18
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum  RemindIntervalType {

    WEEK("1"),
    MONTH("2"),
    YEAR("3")
    ;

    private String code;
}
