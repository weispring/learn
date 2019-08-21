package com.lxc.learn.redis.distributedlock;

import com.lxc.learn.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

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
    private Redisson redisson;

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
                test();
            }
        }).start();

   }

    public void doTask(){
        RLock rLock = redisson.getLock(lockName);
        try {
            if (!rLock.tryLock(1000, releasedTime, TimeUnit.MILLISECONDS)){
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        log.info("已经加锁：{}",lockName );
        int a = 0;
        boolean done = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(releasedTime - 50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true){
                    if (done){
                        boolean result = redisTemplate.expire(lockName,20 , TimeUnit.SECONDS);
                        long ttl = redisTemplate.getExpire(lockName);
                        log.info("续航：{},{}",result,ttl );
                    }
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.setDaemon(true);
        thread.start();

        while (a < 5){
            a = a + 1;
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread.interrupt();

        log.info("{}", "执行完毕");

    }



    public static void main(String[] args) {
        int a = 0;

        while (a < 5){
            a = a + 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    public void test(){
        RLock rLock = redisson.getLock(lockName);
        try {
            if (!rLock.tryLock(1000, releasedTime, TimeUnit.MILLISECONDS)){
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        log.info("已经加锁：{}",lockName );
        int a = 0;
        boolean done = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (done){
                        //TODO WARN无法续航，expire失败？
                        String key = rLock.getName();
                        boolean result = redisTemplate.expire(key,180 , TimeUnit.SECONDS);
                        long ttl = redisTemplate.getExpire(lockName);
                        log.info("续航：{},{},{}",result,ttl,key);
                    }
                }

            }
        });
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.currentThread().sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rLock.unlock();
    }


}
