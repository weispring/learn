package com.lxc.learn.springcloud.client;

import com.alibaba.fastjson.TypeReference;
import com.lxc.learn.common.test.Base64Test;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.springcloud.client.order.Application;
import com.lxc.learn.springcloud.client.order.request.CreateOrderReq;
import com.lxc.learn.springcloud.rpc.facade.ProductRpc;
import com.lxc.learn.test.base.BaseHttpTest;
import com.lxc.learn.test.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/10
 */
@Slf4j
public class OrderTest extends BaseHttpTest {

    private Integer port = 3001;

    @Test
    public void test() throws Exception {
        Req<CreateOrderReq> req = new Req<>();
        CreateOrderReq createOrderReq = new CreateOrderReq();
        req.setBody(createOrderReq);
        createOrderReq.setUserId(123456L);
        List<CreateOrderReq.Sku> products = new ArrayList<>();
        CreateOrderReq.Sku sku = new CreateOrderReq.Sku();
        sku.setCount(2);
        sku.setSkuId(1L);
        products.add(sku);
        createOrderReq.setProducts(products);
        Resp o = invoke("http://localhost:" + port + "/order/api/createorder", req,new TypeReference<Resp>(){});
        log.info("{}",o);
    }

}
