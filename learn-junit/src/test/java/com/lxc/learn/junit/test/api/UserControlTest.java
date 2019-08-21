package com.lxc.learn.junit.test.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lxc.learn.common.util.HttpClientUtil;
import com.lxc.learn.common.util.WebTools;
import com.lxc.learn.common.util.reflect.UnsafeUtils;
import com.lxc.learn.junit.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:59
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class UserControlTest {

    @Autowired
    protected WebApplicationContext applicationContext;

    @Test
    public void addUser() throws Exception {
        User user = new User();
        User o = this.postJson("/user/add", user,new TypeReference<User>(){});
        log.info("{}",o);
    }

    @Test
    public void test() {


        List<User> list1= new ArrayList<>();
        User user;
        for (int i=0;i<3;i++){
            user = new User();
            user.setId(i);
            list1.add(user);
        }
        list1.get(2).setName("------");

        list1.get(1).setName("------");

        user = new User();
        user.setName("363636");
        Long start = System.currentTimeMillis();
        try{
            long offset = UnsafeUtils.getUnsafe().objectFieldOffset(User.class.getDeclaredField("name"));
            Object value = org.springframework.objenesis.instantiator.util.UnsafeUtils.getUnsafe().getObject(user, offset);

            log.info("zhi:{},{}", value.toString(),System.currentTimeMillis() - start);
        }catch (Exception e){
            e.printStackTrace();
        }


        int aa = list1.get(0).hashCode();
        int b = list1.get(1).hashCode();
        int c = list1.get(2).hashCode();
        List<User> list111 = list1.stream().distinct().collect(Collectors.toList());



        List<User> list2= new ArrayList<>();


        User a;
        for (User e : list1) {
            a = e;
            list2.add(a);
        }
        log.info("{}", list2);

        //org.apache.http.examples.client.ClientExecuteSOCKS.java
    }





    protected <T> T postJson(String url, Object req, TypeReference<T> typeReference) throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        String requestBody = JSON.toJSONString(req);
        log.info("test.requestBody={}", requestBody);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody).accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        String content = result.getResponse().getContentAsString();
        log.info("test.result={}", content);

        return JSON.parseObject(content, typeReference);
    }


    public static void main(String[] args) {
        long s = System.currentTimeMillis();


        String url = "http://localhost:8080/test/testGet";

        for (int i = 0; i<500;i++){
            WebTools.get(url);
        }

        long c = System.currentTimeMillis() - s;
        Map map = new HashMap();
        long start = System.currentTimeMillis();
        for (int i = 0; i<500;i++){
            HttpClientUtil.invokeGet(url, map, "UTF-8" ,1000, 1000);

        }

        long a = System.currentTimeMillis() - start;

        log.info("新连接：{}", a);

        log.info("连接池 耗时：{}", c);
        //MainClientExec
    }
}
