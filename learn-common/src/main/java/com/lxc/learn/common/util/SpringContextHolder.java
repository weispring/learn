package com.lxc.learn.common.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/30 16:14
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext;

    public SpringContextHolder() {
    }

    public void setApplicationContext(ApplicationContext context) {
        SpringContextHolder.applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String beanName) {
        assertApplicationContext();

        try {
            return (T)applicationContext.getBean(beanName);
        } catch (Exception var2) {
            log.error("取出bean异常name=" + beanName, var2.getMessage());
            return null;
        }
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        T t = null;

        try {
            t = (T)getApplicationContext().getBean(name, clazz);
        } catch (Exception var4) {
            log.error("取出bean异常name=" + clazz.getName(), var4.getMessage());
        }

        return t;
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();

        try {
            return applicationContext.getBean(requiredType);
        } catch (Exception var2) {
            log.error("取出bean异常class=" + requiredType.getSimpleName(), var2.getMessage());
            return null;
        }
    }

    public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        assertApplicationContext();
        return (DefaultListableBeanFactory)((ConfigurableApplicationContext)applicationContext).getBeanFactory();
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalArgumentException("applicationContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }
}

