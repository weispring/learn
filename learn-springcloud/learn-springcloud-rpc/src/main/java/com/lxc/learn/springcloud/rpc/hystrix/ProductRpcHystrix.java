package com.lxc.learn.springcloud.rpc.hystrix;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.springcloud.rpc.facade.ProductRpc;
import com.lxc.learn.springcloud.rpc.request.SkuReq;
import com.lxc.learn.springcloud.rpc.response.SkuResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * com.netflix.hystrix.HystrixCommandProperties
 *
 * @author lixianchun
 * @description
 * @date 2020/8/7
 */
@Slf4j
@ConditionalOnProperty(
        prefix = "global.feign",
        name = {"enable"},
        havingValue = "true",
        matchIfMissing = true
)
@Component
public class ProductRpcHystrix implements ProductRpc{

    public ProductRpcHystrix(){
        log.info("----------------------");
    }

    @Override
    public Resp<SkuResp> listBySkuIds(Req<SkuReq> req) {
        log.warn("rpc Hystrix: {}",this.getClass().getName());
        return RespUtil.success();
    }

    //com.netflix.hystrix.HystrixCommandProperties
}
