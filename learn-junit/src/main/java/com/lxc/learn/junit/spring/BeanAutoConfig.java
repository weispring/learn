package com.lxc.learn.junit.spring;

import com.lxc.learn.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/24 10:05
 */
@Slf4j
@AutoConfigureAfter(SpringContextHolder.class)
@Configuration
public class BeanAutoConfig implements InitializingBean {
    private AtomicLong counter = new AtomicLong(0L);
    @Resource
    private StandardEnvironment environment;

    public void afterPropertiesSet() {
        /**  此处调用，spirngContexHolder.applicationContext 还没初始化         */
        //registerContainer();
    }

    /**
     * 注册bean进入ioc容器
     * */
    private void registerContainer() {
        //Class<?> clazz = AopUtils.getTargetClass(condition);

        BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.rootBeanDefinition(BeanDefine.class);
        beanBuilder.addPropertyValue("name", "测试名称009");
        beanBuilder.addPropertyValue("id", 10101019L);

        beanBuilder.setDestroyMethodName("destroy");
        String containerBeanName = String.format("%s_%s", BeanDefine.class.getName(), this.counter.incrementAndGet());
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) SpringContextHolder.getApplicationContext();
        beanFactory.registerBeanDefinition(containerBeanName, beanBuilder.getBeanDefinition());
        BeanDefine container = (BeanDefine)beanFactory.getBean(containerBeanName, BeanDefine.class);
        container.test();

        beanFactory.registerSingleton("test009", new BeanDefine());
    }

}
