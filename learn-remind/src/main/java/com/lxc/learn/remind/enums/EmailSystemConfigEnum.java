package com.lxc.learn.remind.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 14:03
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum EmailSystemConfigEnum {

    COMMON_ACCOUNT("2629469408","2629469408@qq.com","mzpdzsysikwfdjij"),
    ONLY_ME("2410308914","2410308914@qq.com","nhbgwxyzokjvdidj");

    private String id;

    private String email;

    private String password;

}
