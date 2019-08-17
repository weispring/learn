package com.lxc.learn.redis.mq.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 20:21
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public Jedis jedis(){
        //连接redis服务器(在这里是连接本地的)
        Jedis jedis = new Jedis(redisProperties.getHost(), 6379);
        //权限认证
        jedis.auth("123456");
       log.info("连接服务成功");
       return jedis;
    }

}
