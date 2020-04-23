package com.lxc.learn.common.util;

import com.lxc.learn.common.util.core.IReturnCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 15:04
 */
@Slf4j
@Getter
@Setter
public class RuntimeBusinessException extends RuntimeException{

    private IReturnCode returnCode;

    public RuntimeBusinessException(IReturnCode returnCode){
        super(returnCode.getCode() + ":" + returnCode.getMsg());
    }

    public RuntimeBusinessException(String msg){
        super(msg);
    }

}
