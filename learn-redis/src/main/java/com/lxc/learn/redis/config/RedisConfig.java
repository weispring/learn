package com.lxc.learn.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
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


    public static boolean pubsub = true;


    public static boolean simple = true;

    @Bean
    public Jedis jedis(){
        //连接redis服务器(在这里是连接本地的)
        Jedis jedis = new Jedis(redisProperties.getHost(), 6379);
        //权限认证
        jedis.auth("123456");
       log.info("连接服务成功");
       return jedis;
    }

    @Bean
    Redisson redissonSentinel() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://"+redisProperties.getHost()+":6379")
                .setPassword(redisProperties.getPassword());

        return (Redisson)Redisson.create(config);
    }



    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnMissingBean(
            name = {"redisTemplate"}
    )
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        this.initDomainRedisTemplate(redisTemplate, this.redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(
            name = {"listOperations"}
    )
    public ListOperations<String, Object> listOperations() {
        return redisTemplate().opsForList();
    }


    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
    }

}
