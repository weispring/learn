package com.lxc.learn.redis.mq.simple;

import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.redis.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

import static com.lxc.learn.redis.config.Constant.ORDER_CREATED_CHANNEL;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 19:54
 */
@ConditionalOnProperty(prefix = "redis",name = "enable",havingValue = "true")
@Component
@Slf4j
public class SmSender {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisProperties redisProperties;

    static Jedis jedis;

    static {
        jedis= new Jedis("47.104.93.125", 6379);
        //权限认证
        jedis.auth("123456");
    }


    public void publishMessage(String channel, Object message) {
        jedis.lpush(channel, JsonUtil.objectToJson(message));
        log.info("频道：{} 发送消息：{}", channel, JsonUtil.objectToJson(message));
    }

    @PostConstruct
    public void send(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true && RedisConfig.simple){
                    i = i + 1;
                    publishMessage(ORDER_CREATED_CHANNEL, "下单"+i);
                    try {
                        Thread.sleep(200000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
