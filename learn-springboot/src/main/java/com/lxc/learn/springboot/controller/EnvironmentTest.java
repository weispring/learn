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
 * todo markw 不同的profiles 会反转顺序，但是同一个profile里面的配置文件的顺序是何时反转的呢？
 StandardEnvironment standardEnvironment = null;
 standardEnvironment.getProperty()
 EnvironmentPostProcessor

 * @author lixianchun
 * @description
 * @date 2020/6/19
 */
@Slf4j
public class EnvironmentTest {

    /**
     * prifile分组CompositePropertySource，每组可能包含多个PropertySource
     *
     * 命令行参数优先解析进入environment中
     * SimpleCommandLinePropertySource
     *
     *
     profiels
     local
     null

     location
     file:./config/,file:./,classpath:/config/, classpath:/

     name
     application

     fileExt和优先级顺序
     properties
     xml
     yml
     yaml
     最后需要说明的是，循环到profile不为空值，处理很复杂，但是profile 不为空时不会添加属性到loader里面

     * 各独立项目之间
     * 可读取依赖jar /resource/config/application.yml
     * /resource/config/application-profiles.yml
     * /resource/application-profiles.yml
     * 相同路径只会读取第一个

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
