package com.lxc.learn.springboot.bean.configurebeforeandafter;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:12
 */
@Slf4j
@Component
public class Bean2 {

    public Bean2(){
        log.info("construction method:{}", this.getClass().getName());
    }

    @PostConstruct
    public void postConstruct(){
        log.info("postConstruct method:{}", this.getClass().getName());
    }

}
