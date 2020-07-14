package com.lxc.learn.springboot.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;

/**
 *
 * @author lixianchun
 * @description
 * @date 2020/7/7
 */
@Slf4j
public class Ioc {

    /**
     *
     * 注册
     *
     * 主类包装为DefinitionLoader

     * 1. spring.factories
     *
     * 2.@Bean 的定位、扫描解析、注册
     * 下面方法是ioc bean解析注册的核心方法,beanNameGenerator（默认AnnotationBeanNameGenerator）设置
     org.springframework.context.annotation.ComponentScanAnnotationParser#parse
     * 3.@Import注册
     *
     * BeanNameGenerator的设置和默认值(AnnotationBeanNameGenerator)
     *
     * ConfigurationCondition带有生效阶段(处理注解的类 ConfigurationCondition 和 SpringBootCondition)
     * org.springframework.context.annotation.ConfigurationClassParser#processConfigurationClass
     * 综上所述，只有Condition不匹配，且Condition判断的阶段，即ConfigurationPhase设置为PARSE_CONFIGURATION时，@Configuration（例子是@Service，相同效果）注解的类将不会加载到内存中。
     * 否则会加载到ioc
     *
     * org.springframework.beans.factory.Aware的设置
     *
     * bean注册的过滤器
     * TypeExcludeFilter
       AutoConfigurationExcludeFilter
       AnnotationTypeFilter

     创建
     入口
     org.springframework.context.support.AbstractApplicationContext#refresh()
     this.finishBeanFactoryInitialization(beanFactory);
     org.springframework.beans.factory.support.DefaultListableBeanFactory#preInstantiateSingletons()
     通过反射创建bean


     //创建 aop Bean
     org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#doCreateBean
     org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#initializeBean
     org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization

     要代理的class 如果是接口，则使用JdkDynamicAopProxy，否则使用CglibAopProxy创建代理

     代理bean 是如何放入beanFactory 中的？
     代理bean 内部结构？

     */

}
