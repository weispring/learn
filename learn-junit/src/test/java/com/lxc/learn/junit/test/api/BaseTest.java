package com.lxc.learn.junit.test.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class BaseTest {

    @Autowired
    protected WebApplicationContext applicationContext;

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
