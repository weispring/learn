package com.lxc.learn.redis.distributedlock;

import com.lxc.learn.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/19 14:14
 */
@Component
@Slf4j
public class LockDemo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisProperties redisProperties;

/*    @Autowired
    private Redisson redisson;*/

    private String lockName = "task_counter";

    private long releasedTime = 10000;

    public static boolean tryLock(String key,Object value,long releasedTime){
        RedisTemplate template = SpringContextHolder.getBean(RedisTemplate.class);
        template.opsForValue().set(key, value, releasedTime);
        return true;
    }


   @PostConstruct
   public void task(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //test();
                doTask();;
            }
        }).start();

   }

    public void doTask(){
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://"+redisProperties.getHost()+":6379")
                .setPassword(redisProperties.getPassword());

        Redisson redisson = (Redisson)Redisson.create(config);

        RLock rLock = redisson.getLock(lockName);
        try {
            if (!rLock.tryLock(1000, releasedTime, TimeUnit.MILLISECONDS)){
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        log.info("已经加锁：{}",lockName);

        final AtomicBoolean finish = new AtomicBoolean();
        try {
            //任务执行
            new RedisEnduranceThread(releasedTime, finish, lockName, redisTemplate).start();
            int a = 0;
            while (a < 5){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = a + 1;
            }
            //thread.interrupt();
            log.info("{}", "执行完毕");
        }finally {
            finish.set(true);
            rLock.unlock();
        }
    }



}
