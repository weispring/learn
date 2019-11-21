package com.lxc.learn.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/29 18:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class HyperLogLogTest {

    public static final String KEY_HYPERLOGLOG = "KEY_HYPERLOGLOG";

    @Autowired
    private StringRedisTemplate redisTemplate;

    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    /**
     * 基数估计
     * 1.基数估计就是在误差可接受的范围内，快速计算基数。
     */
    @Test
    public void testSerBit() {
        Random random = new Random(36);
        for (int i=0;i<200;i++){
            Long n = redisTemplate.opsForHyperLogLog().add(KEY_HYPERLOGLOG,digits[random.nextInt(36)]+"");
            log.info("KEY_HYPERLOGLOG：{}",n);
        }
        log.info("总数：{}", redisTemplate.opsForHyperLogLog().size(KEY_HYPERLOGLOG));
    }
}
