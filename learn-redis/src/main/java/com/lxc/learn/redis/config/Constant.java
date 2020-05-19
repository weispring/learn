package com.lxc.learn.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;

import java.util.Arrays;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 19:55
 */
public class Constant {


    public static String ORDER_CREATED_CHANNEL = "channel:orderCreated";

    public static String ORDER_PAID_CHANNEL = "channel:orderPaid";

    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("sampleCache")));
        return cacheManager;
    }
}
