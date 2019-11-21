package com.lxc.learn.springboot.bean.configurebeforeandafter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Spring 中的 DependsOn 注解可以保证被依赖的bean先于当前bean被容器创建
 *
 * @author lixianchun
 * @Description
 * @date 2019/11/23 16:12
 */
@Slf4j
@Component
@DependsOn("bean1")
public class Bean4 {

    public Bean4(){
        log.info("construction method:{}", this.getClass().getName());
    }

    @PostConstruct
    public void postConstruct(){
        log.info("postConstruct method:{}", this.getClass().getName());
    }

}
