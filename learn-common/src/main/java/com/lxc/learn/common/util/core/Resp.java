package com.lxc.learn.common.util.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:37
 */
@Setter
@Getter
public class Resp<T> extends BaseDto implements Serializable {
    //private Long serialVersionUID = 1L;

    private RespHead head;

    private T body;

    public Resp (){
        head = new RespHead();
    }


}
