package com.lxc.learn.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.spi.LocaleNameProvider;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/26 15:19
 */
@Slf4j
public class TestMain {

    private static final String url = "http://localhost:8081/user";
    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);
    public static void main(String[] args) {

        String json = "{\"userName\":\"nn\",\"id\":\"123\"}";
        User user = JSON.parseObject(json, User.class);


        log.info(JsonUtil.objectToJson(new User()));
        //org.apache.http.conn.ConnectTimeoutException
        MDC.put("traceId", "LXC0214");
        log.info("测试日志中是否有当前自定义log");
        logger.info("测试日志中是否有当前自定义log");
        Map map = new HashMap();
        map.put("1","2");
        HttpClientUtil.postJsonBody(url + "/test",map,"UTF-8");

        //测试超时和无响应 1 超时表示超过指定时间无返回 2 无响应表示返回为空
        HttpClientUtil.postJsonBody(url + "/testSleep10s",map,"UTF-8");
    }


    @Data
    public static class User{

        private String userName;

        //不进行序列化，亦不进行反序列化
        @JsonIgnore
        private Long userId;
    }
}
