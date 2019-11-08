package com.lxc.learn.designpattern.order.req;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 15:05
 */
@Slf4j
@Data
public class OrderReqBody extends BaseDto {

    private Long productId;

    private Integer count;

}
