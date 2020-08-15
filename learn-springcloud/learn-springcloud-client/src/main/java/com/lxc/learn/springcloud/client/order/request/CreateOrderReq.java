package com.lxc.learn.springcloud.client.order.request;

import com.lowagie.text.pdf.PRIndirectReference;
import com.lxc.learn.common.util.core.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/7
 */
@Slf4j
@Setter
@Getter
@Accessors(chain = true)
public class CreateOrderReq extends BaseDto {

    private Long userId;

    private List<Sku> products;


    @Setter
    @Getter
    @Accessors(chain = true)
    public static class Sku extends BaseDto{

        private Long skuId;

        private Integer count;
    }

}
