package com.lxc.learn.redis.config;

import com.lxc.learn.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 14:39
 */

@Configuration
@Slf4j
public class RedisAgent {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ListOperations<String, Object> redisListOperations;

    public RedisAgent() {
    }


    public Long incrementReturnLong(String key, Long incr){
        byte[] rawKey = redisTemplate.getStringSerializer().serialize(key);
        return redisTemplate.execute((connection) -> {
            try {
                return connection.incrBy(rawKey, incr);
            } catch (Exception var8) {
                return 0L;
            }
        }, true);
    }



    public boolean setKey(String key, String value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        return true;
    }

    public boolean setKey(String key, String value, Long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        return true;
    }



    public boolean setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    public String getKey(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}

