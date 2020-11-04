package com.lxc.learn.springcloud.server.service;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.springcloud.rpc.facade.ProductRpc;
import com.lxc.learn.springcloud.rpc.request.SkuReq;
import com.lxc.learn.springcloud.rpc.response.SkuResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/7
 */
@Slf4j
@RestController
public class ProductService implements ProductRpc {

    //@RequestBody
    @Override
    public Resp<SkuResp> listBySkuIds(@RequestBody  Req<SkuReq> req) {
        log.info("rpc provider : {}","listBySkuIds");
        if (req.getBody() == null
                || CollectionUtils.isEmpty(req.getBody().getSkuIds())){
            return RespUtil.fail();
        }

        System.out.println("===============" + req.getBody().getSkuIds().get(0));
        try {
            Thread.sleep(req.getBody().getSkuIds().get(0));
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        List<SkuResp.Sku> list = new ArrayList<>();
        for (Long id : req.getBody().getSkuIds()){
            SkuResp.Sku sku = new SkuResp.Sku();
            sku.setId(id);
            sku.setSkuName("测试商品");
            sku.setPrice(new Random(1000).nextLong() * 100);
            list.add(sku);
        }
        SkuResp skuResp = new SkuResp();
        skuResp.setSkus(list);
        return RespUtil.success(skuResp);
    }
}
