package com.lxc.learn.junit.test.api;

import com.lxc.learn.common.util.HttpClientUtil;
import com.lxc.learn.common.util.WebTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.execchain.MainClientExec;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class HttpTest {

    /**
     * 测试http连接池和长连接
     * @param args
     */
    public static void main(String[] args) {
        long s = System.currentTimeMillis();


        String url = "http://localhost:8080/test/testGet";

        for (int i = 0; i<5000;i++){
            WebTools.get(url);
        }

        long c = System.currentTimeMillis() - s;
        Map map = new HashMap();
        long start = System.currentTimeMillis();
        for (int i = 0; i<500;i++){
            HttpClientUtil.invokeGet(url, map, "UTF-8" ,1000, 1000);

        }

        long a = System.currentTimeMillis() - start;

        Map map1 = new HashMap();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i<500;i++){
            try {
                HttpClientUtil.sendPost(url, "", 1000,1000,map1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Long B = System.currentTimeMillis() - start1;
        log.info("urlConnnect 耗时：{}", B);

        log.info("新连接：{}", a);

        log.info("连接池 耗时：{}", c);
        //MainClientExec
    }
}
