package com.lxc.learn.designpattern.order.control;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.designpattern.order.req.OrderReqBody;
import com.lxc.learn.designpattern.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 15:14
 */
@Slf4j
@RequestMapping("/order")
@RestController
public class OrderApiControl {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/createOrder")
    public Resp ceateOrder(@RequestBody Req<OrderReqBody> req){
        return orderService.createOrder(req);
    }
}
