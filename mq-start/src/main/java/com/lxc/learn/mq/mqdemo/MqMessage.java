package com.lxc.learn.mq.mqdemo;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/24 9:18
 */
@Slf4j
@Data
public class MqMessage extends BaseDto {

    private static final long serialVersionUID = -8626909088670096488L;
    public String id;
    public String mqType;
    public String message;

}
