package com.lxc.learn.junit.mapper;

import com.lxc.learn.junit.entity.OrderBill;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lxc.learn.junit.resp.OrderDetailResp;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
public interface OrderBillMapper extends BaseMapper<OrderBill> {

    OrderDetailResp getOrderDetail(@Param("id") Long id);


}
