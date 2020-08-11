package com.lxc.learn.springcloud.rpc.response;

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
public class SkuResp {

    private List<Sku> skus;

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class Sku{
        private Long id;

        private String skuName;

        private Long price;
    }

}
