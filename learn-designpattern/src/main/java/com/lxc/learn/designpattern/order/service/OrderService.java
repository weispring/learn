package com.lxc.learn.designpattern.order.service;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.designpattern.order.strategy.OrderContextFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
