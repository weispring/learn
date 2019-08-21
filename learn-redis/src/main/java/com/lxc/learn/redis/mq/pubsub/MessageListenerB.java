package com.lxc.learn.redis.mq.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

import static com.lxc.learn.redis.config.Constant.ORDER_CREATED_CHANNEL;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 19:55
 */
@Component
@Slf4j
public class MessageListenerB {

    @Autowired
    private RedisConfig redisConfig;

    static Jedis jedis;

    static {
        jedis= new Jedis("47.104.93.125", 6379);
        //权限认证
        jedis.auth("123456");
    }

    @PostConstruct
    public void consumer(){
        if (redisConfig.pubsub){
            new MessageConsumer(jedis).start();
        }
    }



}
