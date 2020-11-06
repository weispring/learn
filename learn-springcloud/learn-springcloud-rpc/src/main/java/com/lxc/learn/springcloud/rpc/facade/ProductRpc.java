package com.lxc.learn.springcloud.rpc.facade;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.springcloud.rpc.hystrix.ProductRpcHystrix;
import com.lxc.learn.springcloud.rpc.request.SkuReq;
import com.lxc.learn.springcloud.rpc.response.SkuResp;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 当服务合并时，关闭feign,走内部进程通信
 *
 * todo wait implements
 * 提供一个自定义conditon，避免针对fegin是否关闭需要每个地方添加下面属性注解配置
 * @author lixianchun
 * @description
 * @date 2020/8/7
 */
@ConditionalOnProperty(
        prefix = "global.feign",
        name = {"enable"},
        havingValue = "true",
        matchIfMissing = true
)
@FeignClient(
        value = "${fegin.product:eureka-client-product}",
        fallback = ProductRpcHystrix.class
)
@AutoConfigureAfter(ProductRpcHystrix.class)
public interface ProductRpc {

    @LoadBalanced
    @RequestMapping("/product/rpc/listBySkuIds")
    public Resp<SkuResp> listBySkuIds(Req<SkuReq> req);

}
