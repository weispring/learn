package com.lxc.learn.common.util.core;

import lombok.experimental.UtilityClass;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 18:38
 */
@UtilityClass
public class RespUtil {

    public Resp convertResult(boolean success){
        if (success){
            return getRespByCode(ReturnCode.SUCCESS);
        }
        return getRespByCode(ReturnCode.FAIL);
    }

    public static Resp getRespByCode(IReturnCode returnCode){
        Resp resp = new Resp();
        resp.getHead().setCode(returnCode.getCode())
                .setMsg(returnCode.getMsg())
                .setError(returnCode.getError());
        return resp;
    }


    public Resp success(){
        return getRespByCode(ReturnCode.SUCCESS);
    }


    public <T> Resp success(T body){
        Resp resp = getRespByCode(ReturnCode.SUCCESS);
        resp.setBody(body);
        return resp;
    }

    public Resp fail(){
        return getRespByCode(ReturnCode.FAIL);
    }

    public Resp fail(String error){
        Resp resp = getRespByCode(ReturnCode.FAIL);
        resp.getHead().setError(error);
        return resp;
    }
}
