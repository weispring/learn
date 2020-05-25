package com.lxc.learn.junit.util.multilanguage;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/17 15:31
 */
@AllArgsConstructor
@Getter
public enum MultiLanguageEnum {

    ZH_HK("zh-HK","","",1,"中文繁体"),
    ZH_CN("zh-CN","Lan2","_lan2",2,"中文简体"),
    EN_US("en-US","Lan3","_lan3",3,"英文");


    private String code;

    private String fieldSuffix;

    private String SqlfieldSuffix;

    private Integer languageType;
    private String value;

    public static MultiLanguageEnum valueByCode(String code){
        for (MultiLanguageEnum languageEnum : values()){
            if (languageEnum.getCode().equals(code)){
                return languageEnum;
            }
        }
        throw new RuntimeException("can not find enum, the code is " + code);
    }
}