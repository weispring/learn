package com.lxc.learn.junit.test.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import java.util.List;

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
        for (int i=0;i<3;i++){
            User user = new User();
            user.setId(i);
            list1.add(user);
        }


        List<User> list2= new ArrayList<>();


        User a;
        for (User e : list1) {
            a = e;
            list2.add(a);
        }
        log.info("{}", list2);
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


}
