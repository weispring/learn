package com.lxc.learn.redis.mq.pubsub;

import com.lxc.learn.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
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
@Component
@Slf4j
public class MessageSender {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisProperties redisProperties;
    @Autowired
    private RedisConfig redisConfig;

    static Jedis jedis;

    static {
        jedis= new Jedis("47.104.93.125", 6379);
        //权限认证
        jedis.auth("123456");
    }


    public void publishMessage(String channel, Object message) {
        jedis.publish(channel, JsonUtil.objectToJson(message));
        log.info("频道：{} 发送消息：{}", channel, JsonUtil.objectToJson(message));
    }

    @PostConstruct
    public void send(){
        if (redisConfig.simple){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true){
                        i = i + 1;
                        publishMessage(ORDER_CREATED_CHANNEL, "下单"+i);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }


}
