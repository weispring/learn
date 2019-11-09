package com.lxc.learn.common.util.core;

import lombok.AllArgsConstructor;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:55
 */
@AllArgsConstructor
public enum ReturnCode implements IReturnCode {
    SUCCESS("1000","成功"),
    PARAMMISSING("2000","参数缺失"),
    FAIL("5000","失败"),

    ;


    private String code;

    private String msg;


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getError() {
        return null;
    }



    public boolean equals(Resp code){
        if (this.getCode().equals(code.getHead().getCode())){
            return true;
        }
        return false;
    }
}
