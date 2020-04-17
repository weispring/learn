package com.lxc.learn.junit.service.impl;

import com.lxc.learn.junit.entity.OrderItem;
import com.lxc.learn.junit.mapper.OrderItemMapper;
import com.lxc.learn.junit.service.OrderItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品信息 服务实现类
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
