package com.lxc.learn.common.util;

import com.lxc.learn.common.util.core.IReturnCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 15:04
 */
@Slf4j
public class RuntimeBusinessException extends RuntimeException{

    public RuntimeBusinessException(IReturnCode returnCode){
        super(returnCode.getCode() + ":" + returnCode.getMsg());
    }

}
