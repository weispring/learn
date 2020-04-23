package com.lxc.learn.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.*;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/15 20:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class RankTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String SCORE_RANK = "score_rank";


    /**
     * 批量新增
     */
    @Test
    public void batchAdd() {
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>("张三" + i, 1D + i);
            tuples.add(tuple);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        Long num = redisTemplate.opsForZSet().add(SCORE_RANK, tuples);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);
        Long b = System.currentTimeMillis();
        Set set = redisTemplate.keys("*");
        System.out.println(System.currentTimeMillis() - b);

    }


    @Test
    public void batchAdd1() {
        long start = System.currentTimeMillis();
        Random random = new Random(100);
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForZSet().add(SCORE_RANK, "张三" + i, 1D + random.nextInt(100));
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));

        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + 100);
        Long b = System.currentTimeMillis();
        Set set = redisTemplate.keys("*");
        System.out.println(System.currentTimeMillis() - b);

        Set<String> range = redisTemplate.opsForZSet().range(SCORE_RANK, 0, 100);
        System.out.println("获取到的排行列表:" + JSON.toJSONString(range));

        redisTemplate.opsForZSet().add(SCORE_RANK, "张三" + 50, 1D + 500);

        range = redisTemplate.opsForZSet().range(SCORE_RANK, 0, 100);
        System.out.println("获取到的排行列表:" + JSON.toJSONString(range));

    }



    /**
     * 批量新增
     */
    @Test
    public void batchAddValue() {
        Map map = new HashMap(100000 * 4/3);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            map.put("张三" + i, "1" + i);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        //新增多个键值
        redisTemplate.opsForValue().multiSet(map);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));

        Long b = System.currentTimeMillis();
        Set set = redisTemplate.keys("*");
        System.out.println("受影响行数：" + set.size());
        System.out.println(set);
        System.out.println(System.currentTimeMillis() - b);


    }


    /**
     * 获取排行列表
     */
    @Test
    public void list() {

        Set<String> range = redisTemplate.opsForZSet().reverseRange(SCORE_RANK, 0, 10);
        System.out.println("获取到的排行列表:" + JSON.toJSONString(range));
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 10);
        System.out.println("获取到的排行和分数列表:" + JSON.toJSONString(rangeWithScores));
    }


    /**
     * 单个新增，存在则修改
     */
    @Test
    public void add() {
        redisTemplate.opsForZSet().add(SCORE_RANK, "李四", 8899);
    }






    /**
     * 获取单个的排行
     */
    @Test
    public void find(){
        Long rankNum = redisTemplate.opsForZSet().reverseRank(SCORE_RANK, "李四");
        System.out.println("李四的个人排名：" + rankNum);

        Double score = redisTemplate.opsForZSet().score(SCORE_RANK, "李四");
        System.out.println("李四的分数:" + score);
    }


    /**
     * 统计两个分数之间的人数
     * 等值取区间 []
     */
    @Test
    public void count(){
        Long count = redisTemplate.opsForZSet().count(SCORE_RANK, 6, 7);
        Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores = redisTemplate.opsForZSet().rangeByScoreWithScores(SCORE_RANK, 6, 7);
        System.out.println("rangeByScoreWithScores:" + JSON.toJSONString(rangeByScoreWithScores));
        System.out.println("统计8001-9000之间的人数:" + count);
    }

    /**
     * 获取整个集合的基数(数量大小)
     */
    @Test
    public void zCard(){
        Long aLong = redisTemplate.opsForZSet().zCard(SCORE_RANK);
        System.out.println("集合的基数为：" + aLong);
    }


    /**
     * 使用加法操作分数
     */
    @Test
    public void incrementScore(){
        Double score = redisTemplate.opsForZSet().incrementScore(SCORE_RANK, "李四", 1000);
        System.out.println("李四分数+1000后：" + score);
    }

    /**
     * 单个新增
     */
    @Test
    public void addDouble() {

        redisTemplate.opsForValue().increment("key1",new Double(6.60001));
        redisTemplate.opsForValue().increment("key1",new Double(6.60001));
        String d = redisTemplate.opsForValue().get("key1");
        System.out.println(d);
        redisTemplate.multi();
        redisTemplate.keys("*");

        redisTemplate.exec();
    }

    @Test
    public void test(){
        String a = "[{\"value\":\"带机上台\",\"id\":\"1\",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]},{\"value\":\"集客上台\",\"id \":\"2\",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]},{\"value\":\"购机上台\",\"id\":\"3\",\"children\":[{\" id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]},{\"value\":\"旗舰机上台\",\"id\":\"4\",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]},{\" value\":\"学生上台\",\"id\":\"13\",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]},{\"value\":\"简易上台\",\"id\":\"14 \",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id \":\"3\",\"value\":\"预付转后付\"}]},{\"value\":\"现有客户买手机\",\"id\":\"7\"},{\"value\":\"净手机购买\",\"id\":\"12\"},{\"value\":\"普通商品购买\",\"id\":\"0\"},{\"value\":\"缴费\",\"id\":\"5\"},{\"value\":\"储值卡增值\",\" id\":\"6\"},{\"value\":\"BR集客\",\"id\":\"16\",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]},{\"value\":\"极速开台\",\"id\":\"17\",\"children\":[{\"id\":\"1\",\"value\":\"新号上台\"},{\"id\":\"2\",\"value\":\"携号上台\"},{\"id\":\"3\",\"value\":\"预付转后付\"}]}]";

        JSONArray jsonArray = JSON.parseArray(a);

        String aa = null;
    }
}
