package com.lxc.learn.junit.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/24 10:05
 */
@Slf4j
@Configuration
public class BeanAutoConfig implements ApplicationContextAware, InitializingBean {

    private static ConfigurableApplicationContext applicationContext;

    private AtomicLong counter = new AtomicLong(0L);

    @Resource
    private StandardEnvironment environment;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext)applicationContext;
    }


    public void afterPropertiesSet() {
        registerContainer();
    }

    private void registerContainer() {
        //Class<?> clazz = AopUtils.getTargetClass(condition);

        BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.rootBeanDefinition(BeanDefine.class);
        beanBuilder.addPropertyValue("name", "测试名称009");
        beanBuilder.addPropertyValue("id", 10101019L);

        beanBuilder.setDestroyMethodName("destroy");
        String containerBeanName = String.format("%s_%s", BeanDefine.class.getName(), this.counter.incrementAndGet());
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)this.applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(containerBeanName, beanBuilder.getBeanDefinition());
        BeanDefine container = (BeanDefine)beanFactory.getBean(containerBeanName, BeanDefine.class);
        container.test();

        beanFactory.registerSingleton("test009", new BeanDefine());

    }


    public static <T> T getBean(String name, Class<T> clazz) {
        T t = null;
        try {
            t = (T)applicationContext.getBean(name, clazz);
        } catch (Exception var4) {
            log.error("取出bean异常name=" + clazz.getName(), var4.getMessage());
        }
        return t;
    }
}
