package com.lxc.learn.redis.mq.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

import static com.lxc.learn.redis.config.Constant.ORDER_CREATED_CHANNEL;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 19:55
 */
@Component
@Slf4j
public class MessageListenerA {

/*
    @Autowired
    private Jedis jedis;
*/

    public void consumer(String channel, String message){
        log.info("消息消息：{}，{}", channel, message);
    }

    static Jedis jedis;

    static {
        jedis= new Jedis("47.104.93.125", 6379);
        //权限认证
        jedis.auth("123456");
    }

    @PostConstruct
    public void consumer(){
      new Thread(new Runnable() {
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
      }).start();
    }



}
