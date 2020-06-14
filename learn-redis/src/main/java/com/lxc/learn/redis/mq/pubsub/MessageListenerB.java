package com.lxc.learn.redis.mq.pubsub;

import com.lxc.learn.redis.config.RedisConfig;
import com.lxc.learn.redis.mq.AbstractRedisMessageListener;
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
@Component
@Slf4j
public class MessageListenerB extends AbstractRedisMessageListener {


    @PostConstruct
    public void consumer(){
        if (this.getRedisConfig().pubsub){
            new MessageConsumer(this.getJedis()).start();
        }
    }



}
