package com.lxc.learn.springboot.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/30
 */
@Slf4j
public class Bean {

    public static void main(String[] args) {

        //默认bean的名称生成方式org.springframework.context.annotation.AnnotationBeanNameGenerator.generateBeanName，
        // 配置在org.springframework.context.annotation.ComponentScan.nameGenerator()

/*        DefaultListableBeanFactory.preInstantiateSingletons()

        AbstractApplicationContext*/

        //BeanPostProcessor
        //BeanFactoryAware
    }
}
