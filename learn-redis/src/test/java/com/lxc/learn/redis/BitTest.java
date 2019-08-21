package com.lxc.learn.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class BitTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String KEY_SETBIT = "set_bit";


    /**
     * 签到/日活用户数
     * setbit
     */
    @Test
    public void testSerBit() {

        //redisTemplate.delete(KEY_SETBIT);
        Boolean result = redisTemplate.opsForValue().setBit(KEY_SETBIT, 0, true);
        log.info("{}",result );
        String v = redisTemplate.opsForValue().get(KEY_SETBIT);
        log.info("{}",v);
        Boolean r1 = redisTemplate.opsForValue().getBit(KEY_SETBIT,1);
        Boolean v0 = redisTemplate.opsForValue().getBit(KEY_SETBIT,0);
        Boolean r63 = redisTemplate.opsForValue().setBit(KEY_SETBIT,1,true);
        Boolean v63 = redisTemplate.opsForValue().setBit(KEY_SETBIT,63,false);
        //Boolean v64 = redisTemplate.opsForValue().getBit(KEY_SETBIT,64);


        log.info("", "");
    }

    @Autowired
    private Redisson redisson;

    @Test
    public void testLock() throws Exception{
        String command = "if (redis.call('setnx', KEYS[1], ARGV[2]) == 1) then redis.call('pexpire', KEYS[1], ARGV[1]); return 1; end; return 0;";


        //是否可重入，守护线程续航实现(设置一个变量)
        RLock rLock = redisson.getLock("lock");
        boolean l = rLock.tryLock(10000, 10000,TimeUnit.MILLISECONDS);
        boolean l1 = rLock.tryLock(10000, 10000,TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().setIfAbsent("klock", "1");
        redisTemplate.opsForValue().set("kl", "", 10000);
        log.info("", "");

        //
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                redisson.getLock("");
                rLock.tryLock();
            }
        });
    }


    private String lockName = "task_counter";

    private long releasedTime = 120000;


    @Test
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
                        String key = rLock.getName();
                        boolean result = redisTemplate.expire(key,1800 , TimeUnit.SECONDS);
                        long ttl = redisTemplate.getExpire(lockName);
                        log.info("续航：{},{},{}",result,ttl,key);
                    }
                }

            }
        });
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
