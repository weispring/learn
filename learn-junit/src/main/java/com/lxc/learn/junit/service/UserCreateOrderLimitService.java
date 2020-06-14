package com.lxc.learn.junit.service;

import com.lxc.learn.junit.config.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/23
 */
@Slf4j
@Service
public class UserCreateOrderLimitService {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    /**
     * 下单时增加次数，取消订单需要减去
     * @param buyerPhone
     * @return
     */
    public boolean incrByCreateOrder(String buyerPhone){
       return redisTemplate.opsForValue().increment(RedisConstant.getUserCreateOrderLimitKey(buyerPhone),1) <= RedisConstant.USER_CREATE_ORDER_LIMIT_NUM ? true : false;
    }

}
