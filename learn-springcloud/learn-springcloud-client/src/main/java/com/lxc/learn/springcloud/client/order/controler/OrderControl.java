package com.lxc.learn.springcloud.client.order.controler;

import com.alibaba.fastjson.JSON;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.springcloud.client.order.request.CreateOrderReq;
import com.lxc.learn.springcloud.rpc.facade.ProductRpc;
import com.lxc.learn.springcloud.rpc.request.SkuReq;
import com.lxc.learn.springcloud.rpc.response.SkuResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/7
 */
@Slf4j
@RestController
@RequestMapping("/order/api/")
public class OrderControl {

    @Autowired
    private ProductRpc productRpc;

    @RequestMapping("/createorder")
    public Resp create(@RequestBody Req<CreateOrderReq> req){
        //调用商品rpc
        Resp<SkuResp> skuRespResp = listBySkuIds(req);
        log.info("调用商品rpc:{}", JSON.toJSONString(skuRespResp));
        //下单
        return RespUtil.success();
    }

    private Resp<SkuResp> listBySkuIds(@RequestBody Req<CreateOrderReq> req){
        Req<SkuReq> rpcSkuReq = new Req<>();
        SkuReq skuReq = new SkuReq();
        rpcSkuReq.setBody(skuReq);
        skuReq.setSkuIds(req.getBody().getProducts().stream().map(e->e.getSkuId()).collect(Collectors.toList()));
        rpcSkuReq.setHead(req.getHead());
        return productRpc.listBySkuIds(rpcSkuReq);
    }
}
