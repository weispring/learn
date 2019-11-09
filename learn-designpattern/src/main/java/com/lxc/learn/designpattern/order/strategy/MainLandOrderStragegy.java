package com.lxc.learn.designpattern.order.strategy;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 14:50
 */
@Slf4j
@Component
public class MainLandOrderStragegy implements IOrderStrategy{
    @Override
    public Resp createOrder(Req req) {
        return RespUtil.success();
    }

    @Override
    public Resp prepay(Req req) {
        return RespUtil.success();
    }
}
