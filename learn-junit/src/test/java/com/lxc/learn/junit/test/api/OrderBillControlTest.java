package com.lxc.learn.junit.test.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lxc.learn.common.enums.DeleteEnum;
import com.lxc.learn.common.util.HttpClientUtil;
import com.lxc.learn.common.util.WebTools;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.reflect.UnsafeUtils;
import com.lxc.learn.junit.entity.OrderItem;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.mapper.OrderItemMapper;
import com.lxc.learn.junit.req.OrderCreateReq;
import com.lxc.learn.junit.resp.OrderDetailResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:59
 * @Description:
 */

@Slf4j
public class OrderBillControlTest extends BaseTest{

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void create() throws Exception {
        OrderCreateReq req = new OrderCreateReq();
        req.setUserId(123456L);
        List<OrderCreateReq.Sku> skus = new ArrayList<>();
        req.setSkus(skus);

        for (int i=0;i<2;i++){
            OrderCreateReq.Sku sku = new OrderCreateReq.Sku();
            sku.setProductId(123L);
            sku.setSkuId(Math.abs(new Random(10).nextLong()));
            sku.setCount(1L);
            skus.add(sku);
        }
        Resp o = postJson("/orderBill/create", req,new TypeReference<Resp<Long>>(){});
        log.info("{}",o);
    }


    @Test
    public void getOrderDetail() throws Exception {
        Req req = new Req(1251848698542624769L);
        Resp o = postJson("/orderBill/getOrderDetail", req,new TypeReference<Resp<OrderDetailResp>>(){});
        log.info("{}",o);
    }



    @Test
    public void saveItems() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setOrderId(1L);
        item.setProductName("");
        item.setProductId(1L);
        item.setCount(1L);
        item.setSkuId(2L);
        item.setPrice(1L);
        item.setsysAddTime(System.currentTimeMillis());
        item.setsysDelState(DeleteEnum.NORMAL.getCode());
        orderItemMapper.addBatch(Arrays.asList(item,item));
    }






}
