package com.lxc.learn.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/30 16:10
 */
@Slf4j
@Component
public class ObjectCountTask {

    @Autowired
    private Environment environment;

    @Scheduled(cron = "0/5 * * * * ?")
    public void notifyTake() {

    }
}
