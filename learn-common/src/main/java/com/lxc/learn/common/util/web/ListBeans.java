package com.lxc.learn.common.util.web;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 11:37
 */
@Slf4j
@UtilityClass
public class ListBeans {

    public static void list(ConfigurableApplicationContext context){
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
        while (iterator.hasNext()){
            log.info("----{}----", iterator.next());
        }
    }
}
