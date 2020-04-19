package com.lxc.learn.junit.controller;


import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.req.OrderCreateReq;
import com.lxc.learn.junit.resp.OrderDetailResp;
import com.lxc.learn.junit.service.OrderCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@RestController
@RequestMapping("/orderBill")
public class OrderBillController {

    @Autowired
    private OrderCreateService orderCreateService;

    @RequestMapping("/create")
    public Resp create(@RequestBody OrderCreateReq req){
        Long orderId = orderCreateService.create(req);
        return RespUtil.success(orderId);
    }

    @RequestMapping("/getOrderDetail")
    public Resp query(@RequestBody Req<Long> req){
        OrderDetailResp result = orderCreateService.getOrderDetail(req.getBody());
        return RespUtil.success(result);
    }
}

