package com.lxc.learn.redis.mq.simple;

import com.lxc.learn.redis.config.Constant;
import com.lxc.learn.redis.mq.AbstractRedisMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.lxc.learn.redis.config.Constant.ORDER_CREATED_CHANNEL;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/16 19:55
 */
@Component
@ConditionalOnProperty(prefix = "redis",name = "enable",havingValue = "true")
@Slf4j
public class SmListener extends AbstractRedisMessageListener {


    /**
     * 消息消费者有一个问题存在，即需要不停的调用rpop方法查看List中是否有待处理消息。每调用一次都会发起一次连接，这会造成不必要的浪费。也许你会使用Thread.sleep()等方法让消费者线程隔一段时间再消费，但这样做有两个问题：

     1）、如果生产者速度大于消费者消费速度，消息队列长度会一直增大，时间久了会占用大量内存空间。

     2）、如果睡眠时间过长，这样不能处理一些时效性的消息，睡眠时间过短，也会在连接上造成比较大的开销。

     所以可以使用brpop指令，这个指令只有在有元素时才返回，没有则会阻塞直到超时返回null，于是消费端可以将processMessage可以改为这样：

     * brpop支持多个列表(队列)
     * brpop指令是支持队列优先级的，顺序决定。
     * 如果两个列表中都有元素，会优先返回优先级高的列表中的元素，所以这儿优先返回MESSAGE_KEY
     * 0表示不限制等待，会一直阻塞在这儿
     *       由于该指令可以监听多个Key,所以返回的是一个列表
            列表由2项组成，1) key名称，2)数据
     */



    public void consumer(String channel, String message){
        log.info("消息消息：{}，{}", channel, message);
    }


    @PostConstruct
    public void consumer(){
        Jedis jedis = this.getJedis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        //String message = jedis.rpop(Constant.ORDER_CREATED_CHANNEL);
                        List<String> messages = jedis.brpop(0,Constant.ORDER_CREATED_CHANNEL);
                        String key = messages.get(0);
                        String message = messages.get(1);
                        if (!StringUtils.isEmpty(message)){
                            consumer(key, message);
                        }

                    }catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }).start();
    }



}
