package com.lxc.learn.redis.distributedlock;

import com.lxc.learn.common.util.SpringContextHolder;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/19 14:14
 */
public class LockDemo {

    private RedisTemplate redisTemplate;


    public static boolean tryLock(String key,Object value,long releasedTime){
        RedisTemplate template = SpringContextHolder.getBean(RedisTemplate.class);
        template.opsForValue().set(key, value, releasedTime);
        return true;
    }



}
