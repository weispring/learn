package com.lxc.learn.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/22 15:20
 */
@Slf4j
@AllArgsConstructor
@Component
public class PrimaryMyService {

    private static IPrimaryMyBean myBean2;

    @PostConstruct
    public void test() throws Exception{
        log.info("com.lxc.learn.junit.bean.MyService {}", myBean2.getClass().getName());
    }


}