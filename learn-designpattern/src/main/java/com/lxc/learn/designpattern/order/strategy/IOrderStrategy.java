package com.lxc.learn.designpattern.order.strategy;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 14:21
 */
public interface IOrderStrategy {

    /**
     *
     * @param req
     * @return
     */
    Resp createOrder(Req req);


    /**
     * 支付
     * @param req
     * @return
     */
    Resp prepay(Req req);

}
