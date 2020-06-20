package com.lxc.learn.springboot.controller;

import com.lxc.learn.common.util.web.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 配置文件解析优先级 解析流程
 * 核心类org.springframework.boot.context.config.ConfigFileApplicationListener
 *
 * @author lixianchun
 * @description
 * @date 2020/6/19
 */
@Slf4j
public class EnvironmentTest {

    /**
     *
     profiels
     local
     null

     location
     file:./config/,file:./,classpath:/config/, classpath:/

     name
     application

     fileExt
     properties
     xml
     yml
     yaml
     最后需要说明的是，循环到profile不为空值，处理很复杂，但是profile 不为空时不会添加属性到loader里面

     * @param args
     */
    public static void main(String[] args) {
        boolean flag = true;
        Stack<String> profiles = new Stack<>();

        profiles.add("local");
        profiles.add("");

        List<String> locations = Arrays.asList("file:./config/,file:./,classpath:/config/,classpath:/".split(","));
        List<String> names = Arrays.asList("application");
        List<String> fileExts = Arrays.asList("properties,xml,yml,yaml".split(","));
        for(; !profiles.isEmpty();) {
            String profile = profiles.pop();
            for (String l : locations){
                for (String n : names){
                    for (String e : fileExts){
                        //include
                        if ("classpath:/application.yml".equals(l + n + "." +  e) && flag){
                            profiles.add("web");
                            flag = false;
                        }
                        if (StringUtil.isEmpty(profile)){
                            log.info("profle:{},{}",profile, l + n + "." +  e);
                        }else {
                            log.info("profle:{},{}",profile, l + n + "-" + profile + "." +  e);
                        }
                    }
                }
            }
        }

    }
}
