package com.lxc.learn.springcloud.client.order.response;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/7
 */
@Slf4j
@Setter
@Getter
@Accessors(chain = true)
public class CreateOrderResp extends BaseDto {

    private Long id;

}
