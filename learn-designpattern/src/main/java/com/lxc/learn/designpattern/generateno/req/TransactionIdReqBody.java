package com.lxc.learn.designpattern.generateno.req;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 17:48
 */
@Slf4j
@Data
public class TransactionIdReqBody extends BaseDto {

    private Long id;

}
