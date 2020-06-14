package com.lxc.learn.redis.mq;

import com.lxc.learn.common.util.SpringContextHolder;
import com.lxc.learn.redis.config.RedisConfig;
import com.lxc.learn.redis.mq.pubsub.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 19:55
 */
public abstract class AbstractRedisMessageListener {

    private RedisConfig redisConfig;

    private Jedis jedis;

    public AbstractRedisMessageListener(){
        redisConfig = SpringContextHolder.getBean(RedisConfig.class);
        jedis= new Jedis(redisConfig.getRedisProperties().getHost(), redisConfig.getRedisProperties().getPort());
        //权限认证
        jedis.auth(redisConfig.getRedisProperties().getPassword());
    }

    @PostConstruct
    public abstract void consumer();


    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public Jedis getJedis() {
        return jedis;
    }
}
