package com.lxc.learn.springboot.bean.configurebeforeandafter;

import com.lxc.learn.springboot.bean.PrimaryMyBean1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:12
 */
@Slf4j
@Component
@AutoConfigureBefore(Bean2.class)
@ConditionalOnBean(PrimaryMyBean1.class)

public class Bean1 {

    public Bean1(){
        log.info("construction method:{}", this.getClass().getName());
    }

    @PostConstruct
    public void postConstruct(){
        log.info("postConstruct method:{}", this.getClass().getName());
    }

}
