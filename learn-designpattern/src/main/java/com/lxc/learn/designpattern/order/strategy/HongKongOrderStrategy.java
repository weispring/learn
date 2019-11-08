package com.lxc.learn.designpattern.order.strategy;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 14:51
 */
@Slf4j
@Component
public class HongKongOrderStrategy implements IOrderStrategy {

    @Override
    public Resp createOrder(Req req) {
        return null;
    }

    @Override
    public Resp prepay(Req req) {
        return null;
    }
}
