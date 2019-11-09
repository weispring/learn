package com.lxc.learn.designpattern.generateno.service.shanxi;

import com.lxc.learn.designpattern.generateno.service.IGenerateCmccTransactionIdService;
import com.lxc.learn.redis.config.RedisAgent;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractShanxiGenerateCmccTransactionIdService implements IGenerateCmccTransactionIdService {

    @Autowired
    protected RedisAgent redisAgent;

    abstract protected String getCmNoIncrRedisKeyPrefix();

    abstract protected Long getTransactionidIncrMax();
    
}