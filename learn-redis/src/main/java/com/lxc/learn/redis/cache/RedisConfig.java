package com.lxc.learn.redis.cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/19
 */
@Configuration(value = "RedisCacheConfig")
@EnableCaching
public class RedisConfig {

    //@Value("${cache.default.expire-time}")
    private int defaultExpireTime = 10;
    //@Value("${cache.user.expire-time}")
    private int userCacheExpireTime = 10;
    //@Value("${cache.user.name}")
    private String userCacheName = "userCacheName";

    /**
     * 优先级
     * 1.@Cacheable等几个用在方法上的注解
     * 2.@CacheConfig
     * 3.CachingConfigurer
     * 4.默认值 CacheAspectSupport中设置的默认值
     * 缓存管理器
     *
     * @param lettuceConnectionFactory
     * @return
     */

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存管理器管理的缓存的默认过期时间
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(defaultExpireTime))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(userCacheName);
        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(userCacheName, defaultCacheConfig.entryTtl(Duration.ofSeconds(userCacheExpireTime)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }


}

