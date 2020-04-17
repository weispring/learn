package com.lxc.learn.junit.mapper;

import com.lxc.learn.junit.entity.OrderItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 订单商品信息 Mapper 接口
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {


    Integer addBatch(List<OrderItem> orderItems);

    Integer AddByusegeneratedkeys(OrderItem orderItem);
}
