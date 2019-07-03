package com.lxc.learn.common.util.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:39
 */
@Setter
@Getter
@Accessors(chain = true)
public class RespHead implements Serializable {
    private Long serialVersionUID = 1L;

    private String code;

    private String msg;

    private String error;

}
