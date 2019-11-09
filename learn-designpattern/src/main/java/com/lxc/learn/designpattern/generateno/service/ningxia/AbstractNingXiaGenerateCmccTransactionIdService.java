package com.lxc.learn.designpattern.generateno.service.ningxia;

import com.lxc.learn.designpattern.generateno.service.IGenerateCmccTransactionIdService;
import com.lxc.learn.redis.config.RedisAgent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: lixianchun
 * @Date: 2018/12/7 15:55
 * @Description:
 */
public abstract class AbstractNingXiaGenerateCmccTransactionIdService implements IGenerateCmccTransactionIdService {
    @Autowired
    protected RedisAgent redisAgent;

    abstract protected String getCmNoIncrRedisKeyPrefix();

    abstract protected Long getTransactionidIncrMax();

}
