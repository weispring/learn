package com.lxc.learn.common.util.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:35
 */
@Setter
@Getter
public class ReqHead implements Serializable {
    private Long serialVersionUID = 1L;

    private String token;

    private String location;
}
