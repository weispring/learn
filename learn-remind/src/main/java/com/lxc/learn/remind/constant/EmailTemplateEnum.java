package com.lxc.learn.remind.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/21 22:56
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum  EmailTemplateEnum {

    FRIEND_BIRTH("friendBirth"),

    ;

    private String code;
}
