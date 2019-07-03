package com.lxc.learn.common.util.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:33
 */
@Setter
@Getter
public class Req<T> extends BaseDto implements Serializable{
    private Long serialVersionUID = 1L;

    private ReqHead head;

    private T body;

}
