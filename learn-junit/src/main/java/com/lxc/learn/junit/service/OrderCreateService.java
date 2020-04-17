package com.lxc.learn.junit.service;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lxc.learn.common.enums.DeleteEnum;
import com.lxc.learn.junit.entity.OrderBill;
import com.lxc.learn.junit.entity.OrderItem;
import com.lxc.learn.junit.enums.OrderStatusEnum;
import com.lxc.learn.junit.enums.PayStatusEnum;
import com.lxc.learn.junit.req.OrderCreateReq;
import com.lxc.learn.junit.resp.OrderDetailResp;
import com.lxc.learn.junit.service.impl.OrderBillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/17
 */
@Slf4j
@Service
public class OrderCreateService {

    @Autowired
    private OrderBillServiceImpl orderBillServiceImpl;

    /**
     * 下单
     * 代码结构：主流程简洁清晰，每一步的方法入参出参定义明确，可达到易读，易维护的优势
     * 对于异常：可以不return,直接抛出异常，全局捕获处理，优势简单清晰
     * 代码规范：避免魔法值，定义常量或者枚举类，方法功能确定唯一，不要将多个功能混杂在一个方法里面，共用部分需要抽出，避免大量重复代码
     * 文档注释：起码标明类、方法的作用，处理逻辑，以及有可能抛出的异常
     * 业务逻辑：需要保证线程安全、每步操作前需要写入，操作后保存结果，以便做到正确处理故障，还需要保证接口幂等性
     * @param req
     * @return
     */
    public Long create(OrderCreateReq req){
        Long orderId = IdWorker.getId();
        //获取sku信息
        List<OrderCreateReq.Sku> skus = getSkuInfo(req);
        //构建订单项
        List<OrderItem> orderItems = makeOrderItem(skus,orderId);
        //构建订单
        OrderBill orderBill = makeUpOrder(orderId,req,orderItems);
        //保存入库
        orderBillServiceImpl.orderSave(orderBill,orderItems);
        return orderId;
    }

    public OrderDetailResp query(Long id){
        return orderBillServiceImpl.getOrderDetail(id);
    }


    /**
     * 查询商品信息
     * @param req
     * @return
     */
    private List<OrderCreateReq.Sku> getSkuInfo(OrderCreateReq req){
        if (CollectionUtils.isEmpty(req.getSkus())){
            throw new RuntimeException("缺失商品信息");
        }
        List<OrderCreateReq.Sku> reqSkus = req.getSkus();

        List<OrderCreateReq.Sku> skus = new ArrayList<>(reqSkus.size());
        //
        reqSkus.forEach(e->{
            OrderCreateReq.Sku sku = new OrderCreateReq.Sku();
            BeanUtils.copyProperties(e,sku);
            sku.setPrice(2000L);
            sku.setProductName("测试商品");
        });
        return skus;
    }

    /**
     * 构建订单
     * @param orderId
     * @param req
     * @param items
     * @return
     */
    private OrderBill makeUpOrder(Long orderId, OrderCreateReq req, List<OrderItem> items){
        OrderBill orderBill = new OrderBill();
        orderBill.setId(orderId);
        Long sum = items.stream().mapToLong(e->e.getPrice()).sum();
        orderBill.setAmount(sum);
        orderBill.setBuyer(req.getUserId());
        orderBill.setOrderStatus(OrderStatusEnum.WAIT_PAY.getCode());
        orderBill.setPayState(PayStatusEnum.WAIT_PAY.getCode());
        orderBill.setSysAddTime(System.currentTimeMillis());
        orderBill.setSysAddUser(req.getUserId());
        return orderBill;
    }


    /**
     * 组装订单项
     * @param skus
     * @param orderId
     * @return
     */
    private List<OrderItem> makeOrderItem(List<OrderCreateReq.Sku> skus, Long orderId){
        List<OrderItem> items = new ArrayList<>(skus.size());
        for (OrderCreateReq.Sku sku : skus) {
            OrderItem item = new OrderItem();
            item.setProductId(sku.getProductId());
            item.setSkuId(sku.getSkuId());
            item.setCount(sku.getCount());
            item.setPrice(sku.getPrice());
            item.setOrderId(orderId);
            item.setsysDelState(DeleteEnum.NORMAL.getCode());
            item.setsysAddTime(System.currentTimeMillis());
        }
        return items;
    }

}
