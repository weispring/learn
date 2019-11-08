package com.lxc.learn.designpattern.order.resp;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 15:07
 */
@Slf4j
@Data
public class OrderRespBody extends BaseDto{

    private Long orderId;
}
