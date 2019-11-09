package com.lxc.learn.designpattern.order.service;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.ReturnCode;
import com.lxc.learn.designpattern.log.ApiLogDbUtil;
import com.lxc.learn.designpattern.order.strategy.OrderContextFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 15:08
 */
@Slf4j
@Service
public class OrderService {


    @Autowired
    private OrderContextFactory orderContextFactory;

    public Resp createOrder(Req req){
        return orderContextFactory.orderContext(req).createOrder(req);
    }

    public Resp prepay(Req req){
        Date reqStart = new Date();
        Resp resp = null;
        try{
            resp = orderContextFactory.orderContext(req).prepay(req);
        }finally {
            ApiLogDbUtil.insertApiLog("preapy", req, resp, reqStart, new Date(), ReturnCode.SUCCESS.getCode());
        }
        return resp;
    }

}
