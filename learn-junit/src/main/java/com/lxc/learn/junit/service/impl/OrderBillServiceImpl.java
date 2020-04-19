package com.lxc.learn.junit.service.impl;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lxc.learn.common.util.xml.XmlUtil;
import com.lxc.learn.junit.entity.OrderBill;
import com.lxc.learn.junit.entity.OrderItem;
import com.lxc.learn.junit.enums.OrderStatusEnum;
import com.lxc.learn.junit.enums.PayStatusEnum;
import com.lxc.learn.junit.mapper.OrderBillMapper;
import com.lxc.learn.junit.mapper.OrderItemMapper;
import com.lxc.learn.junit.req.OrderCreateReq;
import com.lxc.learn.junit.resp.OrderDetailResp;
import com.lxc.learn.junit.service.OrderBillService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@Service
public class OrderBillServiceImpl extends ServiceImpl<OrderBillMapper, OrderBill> implements OrderBillService {

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Transactional
    public void orderSave(OrderBill orderBill, List<OrderItem> orderItems){
        this.baseMapper.insert(orderBill);
        if (!CollectionUtils.isEmpty(orderItems)){
            this.orderItemMapper.addBatch(orderItems);
        }

    }


    public OrderDetailResp getOrderDetail(Long id){
        return this.baseMapper.getOrderDetail(id);
    }
}
