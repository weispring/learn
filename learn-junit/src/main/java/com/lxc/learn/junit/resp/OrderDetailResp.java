package com.lxc.learn.junit.resp;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.junit.entity.OrderBill;
import com.lxc.learn.junit.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/17
 */
@Getter
@Setter
@Slf4j
public class OrderDetailResp extends OrderBill{

    private List<OrderItem> orderItems;

}
