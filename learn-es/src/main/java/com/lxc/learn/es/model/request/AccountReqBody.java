package com.lxc.learn.es.model.request;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/5 16:04
 */
@Data
@Slf4j
public class AccountReqBody extends BaseDto {

    private Integer age;

    private String gender;

    private String address;

    private String employer;

    private String email;

    private String city;

    private String state;

    private Integer accountNumber;




    private String groupBy;

    private String avgField;


    private String sortBy;

}
