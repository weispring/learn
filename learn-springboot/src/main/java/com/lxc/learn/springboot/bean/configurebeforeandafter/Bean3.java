package com.lxc.learn.springboot.bean.configurebeforeandafter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:12
 */
@Slf4j
@Component
@AutoConfigureAfter(Bean2.class)
public class Bean3 {

    public Bean3(){
        log.info("construction method:{}", this.getClass().getName());
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void postConstruct(){
        log.info("postConstruct method:{}", this.getClass().getName());
    }

}
