package com.lxc.learn.test.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lxc.learn.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/10
 */
@Slf4j
public class BaseHttpTest {

    protected <T> T invoke(String url, Object req, TypeReference<T> typeReference) throws Exception {
        String requestBody = JSON.toJSONString(req);
        log.info("test.requestBody={}", requestBody);
        String result = HttpClientUtil.postJsonBody(url, req);
        log.info("test.result={}", result);
        return JSON.parseObject(result, typeReference);
    }

}
