package com.lxc.learn.springboot.annotation;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/10
 */
@Slf4j
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST,proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter

//此注解无法解决 多个Bean的依赖链中，有一个需要多例的问题，@RequestScope 才可以
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeService {

    private String userName;

}
