package com.lxc.learn.redis.distributedlock;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/28
 */
@Slf4j
public class RedisEnduranceThread extends Thread{

    private Long seconds;

    private AtomicBoolean finish;

    private RedisTemplate redisTemplate;

    private String lockName;

    private Long endurance = 5L;

    public RedisEnduranceThread(Long seconds, AtomicBoolean finish, String lockName, RedisTemplate redisTemplate){
        this.seconds = seconds;
        this.finish = finish;
        this.lockName = lockName;
        this.redisTemplate = redisTemplate;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(seconds - 5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!finish.get()){
            //TODO WARN无法续航，expire失败？redisTemplate默认的序列化工具问题
            //String key = rLock.getName();
            //redisTemplate.opsForValue().set("kk1", key);
            try {
            boolean result = redisTemplate.expire(lockName,endurance , TimeUnit.SECONDS);
            if (!result){
                break;
            }
            long ttl = redisTemplate.getExpire(lockName);
            log.info("续航：{},{}",result,ttl );

                Thread.sleep(endurance);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }catch (RedisConnectionFailureException e){
                log.error("用户线程已执行完毕");
                break;
            }
        }
    }

}
