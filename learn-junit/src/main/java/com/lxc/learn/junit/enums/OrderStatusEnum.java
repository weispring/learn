package com.lxc.learn.junit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/17
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    //10待确认20待支付30已支付40已发货50已完成60已取消70退款中80已退款

    WAIT_CONFRIM(10),
    WAIT_PAY(20),
    PAID(30),
    DELIVERED(40),
    COMPLETED(50),
    CANCELLED(60),
    REFUNDING(70),
    REFUNDED(80),
    ;

    private Integer code;


}
