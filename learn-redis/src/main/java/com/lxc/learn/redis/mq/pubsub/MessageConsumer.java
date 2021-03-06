package com.lxc.learn.redis.mq.pubsub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;

import static com.lxc.learn.redis.config.Constant.ORDER_CREATED_CHANNEL;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/21 19:29
 */
@Slf4j
public class MessageConsumer extends Thread {

    private Jedis jedis;

    public MessageConsumer(Jedis jedis){
        this.jedis = jedis;
    }

    /**
     * subscribe 与 psubscribe 区别:完全匹配和模式匹配
     * pub/sub 实现1：n消费，但是消息并未持久化，可能会丢失
     *
     * 问题
     * 1.redis部署多个节点时，如何保证 只有单个节点消费消息？
     */
    @Override
    public void run() {
        while (true){
            try {
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        consumer(channel, message);
                    }

                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        super.onSubscribe(channel, subscribedChannels);
                    }
                },ORDER_CREATED_CHANNEL);

            }catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
    }


    public void consumer(String channel, String message){
        log.info("消息消息：{}，{}", channel, message);
    }

}
