package com.lxc.learn.web.listener;

import com.lxc.learn.common.util.web.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/9
 */
@Slf4j
@Component
public class ApplicationListener implements org.springframework.context.ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private Environment environment;

    private String appName = "spring.application.name";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("appName: {} ", environment.getProperty(appName));
        if (StringUtil.isEmpty(environment.getProperty(appName))){
            log.warn("spring.application.name not config, logPath is undefined");
        }
    }
}
