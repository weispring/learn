package com.lxc.learn.designpattern.order.strategy;

import com.alibaba.druid.support.json.JSONUtils;
import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.common.util.core.ReturnCode;
import com.lxc.learn.designpattern.order.req.OrderReqBody;
import com.lxc.learn.designpattern.order.util.LocationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 14:43
 */
@Slf4j
@Component
public class OrderContextFactory {

    @Autowired
    private MainLandOrderStragegy mainLandOrderStragegy;
    @Autowired
    private HongKongOrderStrategy hongKongOrderStrategy;

    public OrderContext orderContext(Req req){
        if (LocationUtil.isMainLand(req)){
            return new OrderContext(mainLandOrderStragegy);
        }else {
            return new OrderContext(hongKongOrderStrategy);
        }
    }

    public static void main(String[] args) {
        System.out.println(JsonUtil.objectToJson(new Req<>(new OrderReqBody())));
    }
}
