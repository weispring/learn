package com.lxc.learn.designpattern.order.strategy;

import com.alibaba.druid.support.json.JSONUtils;
import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.common.util.core.ReturnCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 上下文角色（Context）：用来操作策略的上下文环境，屏蔽高层模块（客户端）对策略，算法的直接访问，封装可能存在的变化；

 抽象策略角色（Strategy）：规定策略或算法的行为；

 具体策略角色（ConcreteStrategy）：具体的策略或算法实现；

 策略和上下文的关系：
 在策略模式中，一般情况下都是上下文持有策略的引用，以进行对具体策略的调用。但具体的策略对象也可以从上下文中获取所需数据，
 可以将上下文当做参数传入到具体策略中，具体策略通过回调上下文中的方法来获取其所需要的数据。

 * @author lixianchun
 * @Description
 * @date 2019/11/8 14:43
 */
@Slf4j
public class OrderContext {

    private IOrderStrategy orderStrategy;


    public OrderContext(IOrderStrategy orderStrategy){
        this.orderStrategy = orderStrategy;
    }

    public Resp createOrder(Req req){
        log.info("下单入参：{}", JsonUtil.objectToJson(req));
        Resp resp = orderStrategy.createOrder(req);
        if (ReturnCode.SUCCESS.equals(resp)){
            nofifyMessage(req, resp);
        }
        return RespUtil.success();
    }


    public Resp prepay(Req req){
        log.info("下单入参：{}", JsonUtil.objectToJson(req));
        return orderStrategy.prepay(req);
    }


    private void nofifyMessage(Req req, Resp resp){
        log.info("成功下单:{}", JSONUtils.toJSONString(resp));
    }

}
