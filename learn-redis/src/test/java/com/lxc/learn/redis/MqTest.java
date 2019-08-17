package com.lxc.learn.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/15 20:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class MqTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String SCORE_RANK = "score_rank";


    public static void main(String[] args) throws Exception {
        byte[] bytes = new byte[8192];
        Runtime.getRuntime().exec("cd e: && cd umall/business-provider && git log -2").getOutputStream().write(bytes);

        System.out.println(new String(bytes));
    }

}
